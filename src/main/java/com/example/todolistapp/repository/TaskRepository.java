package com.example.todolistapp.repository;

import com.example.todolistapp.entity.Task;
import com.example.todolistapp.entity.User;
import com.example.todolistapp.enums.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTitle(String title);
    List<Task> findAllById(Long id);
    Optional<Task> findTaskByIdAndUser(Long taskId, User user);
    Page<Task> findTaskByUser(User user, Pageable pageable);
    List<Task> findAllByCategory(Categories categories);
}
