package com.example.todolistapp.serviceImpl;

import com.example.todolistapp.entity.Task;
import com.example.todolistapp.entity.User;
import com.example.todolistapp.pojo.TaskDto;
import com.example.todolistapp.pojo.UserDto;
import com.example.todolistapp.repository.TaskRepository;
import com.example.todolistapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskServiceImplTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, userRepository, mapper);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    //Convert Task Entity to DTO
    private TaskDto mapToDto(Task task){return mapper.map(task, TaskDto.class);}

    //Convert User Entity to DTO
    private UserDto mapToDto(User user){return mapper.map(user, UserDto.class);}

    //Convert Task DTO to Entity
    private Task mapToEntity(TaskDto taskDto){return mapper.map(taskDto, Task.class);}

    //Convert User DTO to Entity
    private User mapToEntity(UserDto userDto){return mapper.map(userDto, User.class);}

    @Test
    void createTask() {
        UserDto Rick = new UserDto(
                "Rick",
                "Sanchez",
                "Ricky",
                "pass1234"
        );
        userRepository.save(mapToEntity(Rick));
        TaskDto newTask = new TaskDto();
        newTask.setTitle("Studying");
        newTask.setTitle("Study for 2 hours.");
        taskService.createTask(1L, newTask);

        Task actual = taskRepository.findById(1L).get();

        assertEquals(1L, actual.getId());
        assertEquals(1L, actual.getUser().getId());
    }

    @Test
    void saveTask() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getTaskByCategory() {
    }

    @Test
    void getTask() {
        UserDto Rick = new UserDto(
                "Rick",
                "Sanchez",
                "Ricky",
                "pass1234"
        );
        userRepository.save(mapToEntity(Rick));
        Task task = new Task();
        userRepository.findByUsername("Ricky").get();
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateTaskCategory() {
    }

    @Test
    void deleteTask() {
    }
}