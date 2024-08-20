package com.example.gadgetsore.repository;

import com.example.gadgetsore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
