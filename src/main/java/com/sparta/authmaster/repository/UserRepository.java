package com.sparta.authmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.authmaster.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
