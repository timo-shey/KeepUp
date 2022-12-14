package com.example.todolistapp.controller;

import com.example.todolistapp.pojo.ApiResponse;
import com.example.todolistapp.pojo.UserDto;
import com.example.todolistapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.getUser(userDto);
        return new ResponseEntity<>(""+ user.getUsername() + " signed in successfully.", HttpStatus.CREATED);
    }
}
