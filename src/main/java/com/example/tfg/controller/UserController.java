package com.example.tfg.controller;

import com.example.tfg.model.User;
import com.example.tfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return  new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
    
    @GetMapping("/login")
    public ResponseEntity<Boolean> getUserLogin(@RequestParam String correo, @RequestParam String pass){
        return new ResponseEntity<>(userService.login(correo,pass),HttpStatus.OK);
    } 

}
