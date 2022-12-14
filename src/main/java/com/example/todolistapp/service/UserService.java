package com.example.todolistapp.service;

import com.example.todolistapp.pojo.ApiResponse;
import com.example.todolistapp.pojo.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto createUser(UserDto user);
    UserDto getUser(UserDto userDto);

    ApiResponse findByUsername(String username);

    ApiResponse findById(Long id);

    ApiResponse findBySearch(String question);

    ApiResponse findAllUsers();
}
