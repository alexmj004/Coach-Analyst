package com.example.tfg.controller;

import com.example.tfg.model.Calendar;
import com.example.tfg.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tfg.service.UserService;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PruebaController {
//rama prueba
    @Autowired
    private UserService userService;

    @Autowired
    private CalendarService calendarService;

    // comprobar que guarda un calendario en la base de datos http://localhost:8080/api/auth/calendar
    //JSON del body: {
    //    "title":"partido oficial",
    //    "description":"partido de liga del sabado",
    //    "start":"2025-03-03T11:00:00",
    //    "end":"2025-03-03T13:00:00",
    //    "location":"jose zorrilla",
    //    "category":"liga"
    //}
    @PostMapping("/calendar")
    public ResponseEntity<Calendar> saveCalendar(@RequestBody Calendar calendar) {

        return new ResponseEntity<>(calendarService.save(calendar), HttpStatus.OK);
    }

    // comprobar que hace login contra la base de datos http://localhost:8080/api/auth/login
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

