package com.example.todolistapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String message;
    private boolean success;
    private T data;

//    public ApiResponse(String request_successful, boolean b, T data) {
//    }
}

