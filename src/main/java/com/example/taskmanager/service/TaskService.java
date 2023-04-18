package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "tasks")
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Cacheable(key = "'findAllTasks'")
    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }

    @Cacheable(key = "'filter-'+#email+'-'+#title")
    public List<Task> filter(String email, String title) {
        return taskRepository.findAllByUserEmail(email)
                .stream()
                .filter((task) -> task.getTitle().contains(title))
                .toList();
    }

    @Cacheable(key = "'filterByTitle-'+#title")
    public List<Task> filterByTitle(String title) {
        return taskRepository.findAll()
                .stream()
                .filter((task -> task.getTitle().contains(title)))
                .toList();
    }

    @Cacheable(key = "'filterByEmail-'+#email")
    public List<Task> filterByEmail(String email) {
        return taskRepository.findAllByUserEmail(email);
    }
}
