package com.pegasus.condominio.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pegasus.condominio.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByusername(String username);
}
