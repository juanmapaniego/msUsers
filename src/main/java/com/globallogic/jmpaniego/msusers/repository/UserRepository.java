package com.globallogic.jmpaniego.msusers.repository;

import com.globallogic.jmpaniego.msusers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);
}
