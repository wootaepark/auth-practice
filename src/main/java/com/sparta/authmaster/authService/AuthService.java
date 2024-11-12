package com.sparta.authmaster.authService;

import org.springframework.stereotype.Service;

import com.sparta.authmaster.controller.dto.RegisterDto;
import com.sparta.authmaster.entity.User;
import com.sparta.authmaster.passwordEncoder.PasswordEncoder;
import com.sparta.authmaster.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void register(RegisterDto req) {
		if(userRepository.existsByUsername(req.username())){
			throw new IllegalArgumentException("Username is already in use");
		}
		if(userRepository.existsByEmail(req.email())){
			throw new IllegalArgumentException("Email is already in use");
		}

		if(!passwordEncoder.matches(req.password(), passwordEncoder.encode(req.password()))) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		User user = User.builder()
			.username(req.username())
			.email(req.email())
			.joinPath(req.joinPath())
			.password(passwordEncoder.encode(req.password()))
			.build();

		userRepository.save(user);

	}

}
