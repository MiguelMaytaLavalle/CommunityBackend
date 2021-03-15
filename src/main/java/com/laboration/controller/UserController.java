package com.laboration.controller;

import java.util.List;

import com.laboration.DTO.UserWrapper;

import com.laboration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path="/user")
    public @ResponseBody List<String> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping(path="/user/{username}")
    public @ResponseBody UserWrapper getUserByUsername(@PathVariable String username){
        return service.findByUsername(username);
    }

    @PostMapping(path="/signup")
    public UserWrapper signUpRequest(@RequestBody UserWrapper user){
        return service.signUpRequest(user);
    }

    @GetMapping(path="/inbox/{username}")
    public @ResponseBody List<String> getSenders(@PathVariable String username){
        System.out.println(username);
        return service.getSenders(username);
    }
}

