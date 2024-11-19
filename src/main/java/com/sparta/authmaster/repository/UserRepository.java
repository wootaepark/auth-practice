package com.sparta.authmaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.authmaster.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	User findByUsername(String username);
}
