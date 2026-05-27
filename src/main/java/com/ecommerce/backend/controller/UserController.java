package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
public User addUser(@Valid @RequestBody User user) {
    return userService.saveUser(user);
}

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

   
}