package com.example.todolistfirebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {
    public String name;
    public String password;
    public String email;
    public ArrayList<Task> tasks;
    public String token;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name,String email,String token) {
        this.name = name;
        this.email = email;
        this.token = token;
        this.tasks = new ArrayList<>();
        Task task = new Task("Task 1", "Description 1");
        this.tasks.add(task);
        Task task2 = new Task("Task 2", "Description 2");
        this.tasks.add(task2);
    }


}
