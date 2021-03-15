package com.laboration.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public List<Post> getListOfPosts() {
        return listOfPosts;
    }

    public void setListOfPosts(List<Post> listOfPosts) {
        this.listOfPosts = listOfPosts;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> listOfPosts;

    /*@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalMessage> listOfPMsr;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalMessage> listOfPMss;*/

    public User(){

    }

    protected User(String username, String password) {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
