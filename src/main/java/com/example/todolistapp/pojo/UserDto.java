package com.example.todolistapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

//    @NotNull(message="First name cannot be missing or empty")
//    @Size(min=2, message="First name must not be less than 2 characters")
    private String firstName;

    private String lastName;

    private String username;

    private String password;

}
