package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Trace;
import com.example.taskmanager.repository.TraceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TraceController {

    private final TraceRepository traceRepository;

    public TraceController(TraceRepository traceRepository) {
        this.traceRepository = traceRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Trace>> getTraces() {
        List<Trace> traces = traceRepository.findAll();
        return new ResponseEntity<>(traces, HttpStatus.OK);
    }
}
