package com.example.todolistapp.service;

import com.example.todolistapp.entity.Task;
import com.example.todolistapp.entity.User;
import com.example.todolistapp.enums.Categories;
import com.example.todolistapp.pojo.PostResponse;
import com.example.todolistapp.pojo.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    TaskDto createTask(Long userId, TaskDto taskDto);

    Task saveTask(User user, String title, String description, Categories category);

    //Pagination and Sorting
    PostResponse getAllTasks(Long userId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<Task> getTaskByCategory(Categories categories);

    TaskDto getTask(Long userId, Long taskId);

    TaskDto updateTask(TaskDto updateTask, Long userId, Long taskId);

    TaskDto updateTaskCategory(Long userId, Long taskId, String category);

    void deleteTask(Long userId, Long taskId);
}
