package com.example.todolistapp.util;


import com.example.todolistapp.pojo.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResponseManager<T>{
    public ApiResponse<T> success(T data){
        return new ApiResponse<T>("Request successful", true, data);
    }

    public ApiResponse<T>  error(String errorMessage){
        return new ApiResponse<T>(errorMessage, false, null);
    }
}
