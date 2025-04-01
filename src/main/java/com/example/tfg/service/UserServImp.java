package com.example.tfg.service;

import com.example.tfg.model.User;
import com.example.tfg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }


    @Override
    public Boolean deleteById(int id) {
        return null;
    }

    @Override
    public Boolean update(User user) {
        return null;
    }

}
