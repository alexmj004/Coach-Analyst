package com.example.tfg.service;

import com.example.tfg.model.User;

public interface UserService {
    User addUser(User user);
    boolean login(String name, String password);

}
