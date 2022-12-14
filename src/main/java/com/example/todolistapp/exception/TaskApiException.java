package com.example.todolistapp.exception;

import org.springframework.http.HttpStatus;

public class TaskApiException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public TaskApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public TaskApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
