package com.example.tfg.repository;

import com.example.tfg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name = :name AND u.password = :password")
    User findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
