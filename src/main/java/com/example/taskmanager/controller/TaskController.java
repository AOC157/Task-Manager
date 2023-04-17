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

    @GetMapping("/find/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Task task = taskService.findTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/find/user/{id}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable("id") Long userId) {
        List<Task> tasks = taskService.findTasksByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
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

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task updatedTask = taskService.updateTask(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
