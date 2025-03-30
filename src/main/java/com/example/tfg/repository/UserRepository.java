package com.example.tfg.repository;

import com.example.tfg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Método comprobación credenciales.
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User findByNameAndPassword(@Param("email") String email, @Param("password") String password);

    // Método buscar por nombre usuario.
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

}
