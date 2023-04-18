package com.example.taskmanager.component;

import com.example.taskmanager.entity.Trace;
import com.example.taskmanager.repository.TraceRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class TraceStoringComponent implements Filter {

    private final TraceRepository traceRepository;

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

        if (!request.getRequestURI().equals("/history")) {
            Trace trace = new Trace();
            trace.setRequest(request.getRequestURI());
            trace.setResponse(responseStr);
            trace.setDate(LocalDateTime.now());

            traceRepository.save(trace);
        }
        responseCacheWrapperObject.copyBodyToResponse();

    }

}