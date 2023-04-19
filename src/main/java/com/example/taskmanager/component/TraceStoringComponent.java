package com.example.taskmanager.component;

import com.example.taskmanager.entity.Trace;
import com.example.taskmanager.entity.enumeration.Method;
import com.example.taskmanager.repository.TraceRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;


@Component
public class TraceStoringComponent implements Filter {

    private final TraceRepository traceRepository;
    private static final Logger logger = LoggerFactory.getLogger(TraceStoringComponent.class);

    @Autowired
    public TraceStoringComponent(TraceRepository traceRepository) {
        this.traceRepository = traceRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        ContentCachingRequestWrapper requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(response);

        chain.doFilter(requestCacheWrapperObject, responseCacheWrapperObject);

        byte[] requestArray = requestCacheWrapperObject.getContentAsByteArray();
        String requestBody = new String(requestArray, requestCacheWrapperObject.getCharacterEncoding()).replace("\n", "");

        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseBody = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding()).replace("\n", "");

        if (!request.getRequestURI().equals("/history")) {
            logTrace(request, requestBody, response, responseBody);
            saveTrace(request, requestBody, response, responseBody);
        }

        responseCacheWrapperObject.copyBodyToResponse();

    }

    private void logTrace(HttpServletRequest request, String requestBody, HttpServletResponse response, String responseBody) {
        logger.info("Request: {} {} - Body: {}", request.getMethod(), request.getRequestURI(), requestBody);
        logger.info("Response of {} {}: Body: {} - Status: {}", request.getMethod(), request.getRequestURI(), responseBody, response.getStatus());
    }

    private void saveTrace(HttpServletRequest request, String requestBody, HttpServletResponse response, String responseBody) {
        Trace trace = new Trace();
        trace.setRequestBody(requestBody);
        trace.setRequestURI(request.getRequestURI());
        trace.setMethod(Method.valueOf(request.getMethod()));
        trace.setResponseBody(responseBody);
        trace.setStatus(response.getStatus());
        trace.setDate(LocalDateTime.now());

        traceRepository.save(trace);
    }

}