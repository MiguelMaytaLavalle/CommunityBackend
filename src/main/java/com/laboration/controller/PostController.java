package com.laboration.controller;

import com.laboration.DTO.PostWrapper;
import com.laboration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class PostController {
    @Autowired
    private PostService service;

    @PostMapping(path="/post")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody PostWrapper createPost(@RequestBody PostWrapper post){
        return service.createPost(post);
    }

    @GetMapping(path="/post/{username}")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody List<PostWrapper> getPosts(@PathVariable String username){
        return service.getPosts(username);
    }
}

