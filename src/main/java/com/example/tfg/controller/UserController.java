package com.example.tfg.controller;

import com.example.tfg.model.User;
import com.example.tfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "Usuario añadido correctamente: "+user.getName()+" "+user.getSurname();
    }

    @GetMapping("/login")
    public ResponseEntity<Boolean> getUserLogin(@RequestParam String correo, @RequestParam String pass){
        return new ResponseEntity<>(userService.login(correo,pass),HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Optional<User>> getUser(@RequestParam int id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/getByUserName")
    public ResponseEntity<User> getByUserName(@RequestParam String userName){
        return new ResponseEntity<>(userService.findByUserName(userName),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
     return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

    // Eliminar usuario por id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam int id){
        boolean isDeleted = userService.deleteById(id);

        if (isDeleted) {
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar usuario
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        boolean isUpdated = userService.update(user); // Llama al servicio para actualizar al usuario

        if (isUpdated) {
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }


}
