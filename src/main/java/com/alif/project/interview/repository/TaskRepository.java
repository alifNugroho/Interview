package com.alif.project.interview.repository;

import com.alif.project.interview.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select ts from Task ts where ts.statusTask = 0")
    List<Task> findBystatusTaskNotCompleted();
}
