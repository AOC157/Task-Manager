package com.example.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private Long id;
    private Long userId;
    private String title;
    private Boolean completed;

}
