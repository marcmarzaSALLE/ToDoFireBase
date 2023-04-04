package com.example.todolistfirebase.model;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;
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

    public User(String name,String email,String token,String password) {
        this.name = name;
        this.email = email;
        this.password = encryptPassword(password);
        this.token = token;
        this.tasks = new ArrayList<>();

    }

    private String encryptPassword(String password){
        password = DigestUtils.md5Hex(password);
        return password;
    }


}
