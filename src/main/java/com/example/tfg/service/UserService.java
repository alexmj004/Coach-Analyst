package com.example.tfg.service;

import com.example.tfg.model.Player;
import com.example.tfg.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Añadir un nuevo usuario(coach).
    User addUser(User user);
    // Logear usuario
    boolean login(String name, String password);
    // Obtener jugador por nombre.
    User findByUserName(String username);
    // Obtener todos.
    List<User> findAll();
    // Obtener un usuario por su ID
    Optional<User> findById(int id);
    // Eliminar un usuario por su ID
    Boolean deleteById(int id);
    // Actualizar un usuario
    Boolean update(User user);
}
