package com.example.taskmanager.entity;

import com.example.taskmanager.entity.enumeration.Method;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String requestURI;
    private Method method;
    private String requestBody;
    @Column(columnDefinition = "LONGTEXT")  //Response body may be very large, so I use "LONGTEXT" datatype to store this field in database
    private String responseBody;
    private Integer status;
    private LocalDateTime date;

}
