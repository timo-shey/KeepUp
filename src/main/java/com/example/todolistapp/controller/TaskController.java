package com.example.todolistapp.controller;

import com.example.todolistapp.entity.User;
import com.example.todolistapp.pojo.ApiResponse;
import com.example.todolistapp.pojo.PostResponse;
import com.example.todolistapp.pojo.TaskDto;
import com.example.todolistapp.service.TaskService;
import com.example.todolistapp.service.UserService;
import com.example.todolistapp.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user/{userId}/task")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@PathVariable(name = "userId") Long userId, @RequestBody TaskDto taskDto){
        return new ResponseEntity<>(taskService.createTask(userId, taskDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllTasks(
            @PathVariable Long userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
    return ResponseEntity.ok(taskService.getAllTasks(userId, pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTasksById(@PathVariable(name = "taskId") Long taskId,
                                                @PathVariable(name = "userId") Long userId){
        return ResponseEntity.ok(taskService.getTask(userId, taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto,
                                              @PathVariable(name = "taskId") Long taskId,
                                              @PathVariable(name = "userId") Long userId){
        TaskDto taskResponse = taskService.updateTask(taskDto, userId, taskId);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/set_category")
    public ResponseEntity<TaskDto> updateTaskCategory(@PathVariable(name = "taskId") Long taskId,
                                                      @PathVariable(name = "userId") Long userId,
                                                      @RequestParam("category") String category){
        TaskDto taskResponse = taskService.updateTaskCategory(userId, taskId, category);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask (@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "taskId") Long taskId){
        new ResponseEntity<>("Task has been deleted successfully", HttpStatus.NO_CONTENT);

        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>("Task has been deleted successfully", HttpStatus.NO_CONTENT);
    }
}
