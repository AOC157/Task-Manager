package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Cacheable(key = "'findAllTasks'")
    public List<TaskDTO> findAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDto(tasks);
    }

    @Cacheable(key = "'filter-'+#email+'-'+#title")
    public List<TaskDTO> filter(String email, String title) {
        List<Task> tasks = taskRepository.findAllByUserEmail(email)
                .stream()
                .filter((task) -> task.getTitle().contains(title))
                .toList();
        return taskMapper.toDto(tasks);
    }

    @Cacheable(key = "'filterByTitle-'+#title")
    public List<TaskDTO> filterByTitle(String title) {
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .filter((task -> task.getTitle().contains(title)))
                .toList();
        return taskMapper.toDto(tasks);
    }

    @Cacheable(key = "'filterByEmail-'+#email")
    public List<TaskDTO> filterByEmail(String email) {
        List<Task> tasks = taskRepository.findAllByUserEmail(email);
        return taskMapper.toDto(tasks);
    }
}
