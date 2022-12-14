package com.example.todolistapp.serviceImpl;

import com.example.todolistapp.entity.User;
import com.example.todolistapp.exception.ResourceNotFoundException;
import com.example.todolistapp.pojo.ApiResponse;
import com.example.todolistapp.pojo.UserDto;
import com.example.todolistapp.repository.UserRepository;
import com.example.todolistapp.service.UserService;
import com.example.todolistapp.util.ResponseManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResponseManager responseManager;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();

        BeanUtils.copyProperties(userDto, user);

        boolean userExist = userRepository.existsByUsername(userDto.getUsername());
        if(userExist)
            throw new IllegalStateException("Username Already Exists!");

//        User newUser= new User();
//        newUser.setFirstName(userDto.getFirstName());
//        newUser.setLastName(userDto.getLastName());
//        newUser.setUsername(userDto.getUsername());
//        newUser.setPassword(userDto.getPassword());

        User savedUser = userRepository.save(user);

        UserDto savedUserDto = new UserDto();
        BeanUtils.copyProperties(savedUser, savedUserDto);
        return savedUserDto;
    }

    @Override
    public UserDto getUser(UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null)
            new ResponseEntity<>("Complete all fields", HttpStatus.BAD_REQUEST);
//            throw new IllegalStateException("Complete all fields");
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(()-> {
            throw new IllegalStateException("User not found");
        });
        if(!user.getPassword().equals(userDto.getPassword())) throw new IllegalStateException("Password Incorrect");
        UserDto userDto1 = new UserDto();
        BeanUtils.copyProperties(user, userDto1);
        return userDto1;
    }

    @Override
    public ApiResponse findByUsername(String username) {
        User foundUser = userRepository. findByUsername(username).orElse(null);

        if(foundUser != null){
            return responseManager.success(foundUser);
        }
        return responseManager.error("User not found!");
    }

    @Override
    public ApiResponse findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null)
            return responseManager.success(user);
        return responseManager.error("User not found");
    }

    @Override
    public ApiResponse findBySearch(String question) {
        List<User> userList = userRepository.findBySearch(question).orElse(null);
        assert userList != null;
        if(!userList.isEmpty())
            return responseManager.success(userList);
        return responseManager.error("User not found!");
    }

    @Override
    public  ApiResponse findAllUsers(){
        List<User> userList = userRepository.findAll();
        if(!userList.isEmpty())
            return responseManager.success(userList);

        return  responseManager.error("No user available");
    }


}
