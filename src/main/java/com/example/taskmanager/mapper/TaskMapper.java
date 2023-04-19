package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    TaskDTO toDto(Task task);

    @Mapping(source = "user.id", target = "userId")
    List<TaskDTO> toDto(List<Task> tasks);
}

