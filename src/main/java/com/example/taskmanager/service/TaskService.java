package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("task by id " + id + " was not found"));
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public List<Task> findTasksByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public List<Task> filter(String email, String title) {
        return taskRepository.findAllByUserEmail(email)
                .stream()
                .filter((task) -> task.getTitle().contains(title))
                .toList();
    }

    public List<Task> filterByTitle(String title) {
        return taskRepository.findAll()
                .stream()
                .filter((task -> task.getTitle().contains(title)))
                .toList();
    }

    public List<Task> filterByEmail(String email) {
        return taskRepository.findAllByUserEmail(email);
    }
}
