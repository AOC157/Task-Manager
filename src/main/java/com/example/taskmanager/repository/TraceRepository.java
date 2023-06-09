package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceRepository extends JpaRepository<Trace, Long> {
}
