package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * <p>Resource: {@link com.example.taskmanager.mapper.TaskMapperImpl}</p>
 * <p>Description: This class is used to map task entity to task dto. we will return dto instead of entity as APIs responses</p>
 * <p>{@code TaskMapperImpl} is an auto-generated class which implements {@code TaskMapper} interface methods</p>
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    TaskDTO toDto(Task task);

    @Mapping(source = "user.id", target = "userId")
    List<TaskDTO> toDto(List<Task> tasks);
}

