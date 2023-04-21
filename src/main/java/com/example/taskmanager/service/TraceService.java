package com.example.taskmanager.service;

import com.example.taskmanager.entity.Trace;
import com.example.taskmanager.repository.TraceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraceService {

    private final TraceRepository traceRepository;

    public TraceService(TraceRepository traceRepository) {
        this.traceRepository = traceRepository;
    }

    public List<Trace> findAllTraces() {
        return traceRepository.findAll();
    }
}
