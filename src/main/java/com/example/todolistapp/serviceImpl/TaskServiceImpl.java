package com.example.todolistapp.serviceImpl;

import com.example.todolistapp.entity.Task;
import com.example.todolistapp.entity.User;
import com.example.todolistapp.enums.Categories;
import com.example.todolistapp.exception.ResourceNotFoundException;
import com.example.todolistapp.pojo.PostResponse;
import com.example.todolistapp.pojo.TaskDto;
import com.example.todolistapp.repository.TaskRepository;
import com.example.todolistapp.repository.UserRepository;
import com.example.todolistapp.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public TaskDto createTask(Long userId, TaskDto taskDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        taskRepository.save(task);
        return mapToDto(task);
    }

    @Override
    public Task saveTask(User user, String title, String description, Categories category) {
        Task task = new Task();
        User users = userRepository.findByUsername(user.getUsername()).orElseThrow();
        task.setUser(users);
        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        task.setCompletedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public PostResponse getAllTasks(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Create Pageable Instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Task> tasks = taskRepository.findTaskByUser(user, pageable);

        //Get content from page object
        List<Task> listOfPosts = tasks.getContent();

        List<TaskDto> content = listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(tasks.getNumber());
        postResponse.setPageSize(tasks.getSize());
        postResponse.setTotalElements(tasks.getTotalElements());
        postResponse.setTotalPages(tasks.getTotalPages());
        postResponse.setLast(tasks.isLast());

        return postResponse;
    }

    @Override
    public List<Task> getTaskByCategory(Categories categories) {
        return taskRepository.findAllByCategory(categories);
    }

    @Override
    public TaskDto getTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        Task task = taskRepository.findTaskByIdAndUser(taskId, user).orElseThrow(
                ()-> new ResourceNotFoundException("Tasks", "id", taskId));

        return mapToDto(task);
    }

    @Override
    @Transactional
    public TaskDto updateTask(TaskDto updateTask, Long userId, Long taskId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        Task task = taskRepository.findTaskByIdAndUser(taskId, user).orElseThrow(
                ()-> new ResourceNotFoundException("tasks", "id", taskId));

        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setCategory(updateTask.getCategory());
        task.setUpdatedAt(LocalDateTime.now());

        Task newTask = taskRepository.save(task);
        return mapToDto(newTask);
    }

    @Override
    @Transactional
    public TaskDto updateTaskCategory(Long userId, Long taskId, String category) {

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        Task task = taskRepository.findTaskByIdAndUser(taskId, user).orElseThrow(
                ()-> new ResourceNotFoundException("tasks", "id", taskId));

        Categories statusCheck = checkStatus(category);

        if(!task.getCategory().equals(statusCheck)){
            task.setCategory(statusCheck);
            if(statusCheck.equals(Categories.DONE)){
                task.setCompletedAt(LocalDateTime.now());
                task.setUpdatedAt(LocalDateTime.now());
            } else {
                task.setUpdatedAt(LocalDateTime.now());
                task.setCompletedAt(null);
            }
        }

        TaskDto taskDto = new TaskDto();
//        task.setTitle(taskDto.getTitle());
//        task.setDescription(taskDto.getDescription());
//        task.setCategory(updateTask.getCategory());
//        task.setUpdatedAt(new Date());
//        task.setCompletedAt(new Date());
        BeanUtils.copyProperties(task, taskDto);

//        Task newTask = taskRepository.save(task);
//        return mapToDto(newTask);
        return taskDto;
    }

    @Override
    public void deleteTask(Long userId, Long taskId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        Task task = taskRepository.findTaskByIdAndUser(taskId, user).orElseThrow(
                ()-> new ResourceNotFoundException("Tasks", "id", taskId));

        taskRepository.delete(task); }


    //Convert Entity to DTO
    private TaskDto mapToDto(Task task){
        return mapper.map(task, TaskDto.class);
    }

    //Convert DTO to Entity
    private Task mapToEntity(TaskDto taskDto){
        return mapper.map(taskDto, Task.class);
    }

    private Categories checkStatus(String status){
        Categories statusCheck;
        if(status.equalsIgnoreCase("pending")) statusCheck = Categories.PENDING;
        else if(status.equalsIgnoreCase("in_progress")) statusCheck = Categories.IN_PROGRESS;
        else if(status.equalsIgnoreCase("done")) statusCheck = Categories.DONE;
        else throw new IllegalStateException("Invalid Category");

        return statusCheck;
    }

}
