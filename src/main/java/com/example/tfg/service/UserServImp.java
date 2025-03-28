package com.example.tfg.service;

import com.example.tfg.model.User;
import com.example.tfg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean login(String name, String password) {
        User user = userRepository.findByNameAndPassword(name, password);
        return user != null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

}
