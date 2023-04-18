package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.filter.TaskFilter;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(@RequestBody TaskFilter filter) {
        List<Task> tasks;
        if (filter.getEmail() != null && filter.getTitle() != null) {
            tasks = taskService.filter(filter.getEmail(), filter.getTitle());
        } else if (filter.getEmail() == null && filter.getTitle() != null) {
            tasks = taskService.filterByTitle(filter.getTitle());
        } else if (filter.getEmail() != null && filter.getTitle() == null) {
            tasks = taskService.filterByEmail(filter.getEmail());
        } else {
            tasks = taskService.findAllTasks();
        }

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
