package com.example.tfg.controller;

import com.example.tfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PruebaController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {
        boolean isValid = userService.login(name, password);

        if (isValid) {
            return ResponseEntity.ok().body(Map.of("message", "Login exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales incorrectas"));
        }
    }
}

