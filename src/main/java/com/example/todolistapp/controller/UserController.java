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
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto){
        userService.createUser(userDto);
        return new ResponseEntity<>(""+ userDto.getFirstName()
                + "'s account has been successfully created.", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ApiResponse findUserById(@PathVariable Long id){
        return userService.findById(id);
    }


    @GetMapping("/get_username")
    public ApiResponse findUserByUsername(@RequestParam("username") String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/search")
    public ApiResponse findUserBySearch(@RequestParam("q") String question){
        return userService.findBySearch(question);
    }
}
