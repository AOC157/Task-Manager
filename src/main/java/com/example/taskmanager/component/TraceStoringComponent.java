package com.example.taskmanager.component;

import com.example.taskmanager.entity.Trace;
import com.example.taskmanager.repository.TraceRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(response);
        chain.doFilter(request, responseCacheWrapperObject);

        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());

        logTrace(request, response, responseStr);

        saveTrace(request, responseStr);

        responseCacheWrapperObject.copyBodyToResponse();

    }

    private void logTrace(HttpServletRequest request, HttpServletResponse response, String responseStr) {
        logger.info("Request: {} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
        logger.info("Response of {} {}: {} with status {}", request.getMethod(), request.getRequestURI(), responseStr, response.getStatus());
    }

    private void saveTrace(HttpServletRequest request, String responseStr) {
        if (!request.getRequestURI().equals("/history")) {
            Trace trace = new Trace();
            trace.setRequest(request.getMethod() + " " + request.getRequestURI());
            trace.setResponse(responseStr);
            trace.setDate(LocalDateTime.now());

            traceRepository.save(trace);
        }
    }

}