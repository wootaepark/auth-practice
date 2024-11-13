package com.sparta.authmaster.authService;

import org.springframework.stereotype.Service;

import com.sparta.authmaster.controller.dto.LoginDto;
import com.sparta.authmaster.controller.dto.LoginSuccessResp;
import com.sparta.authmaster.controller.dto.RegisterDto;
import com.sparta.authmaster.entity.User;
import com.sparta.authmaster.jwtHelper.JwtHelper;
import com.sparta.authmaster.passwordEncoder.PasswordEncoder;
import com.sparta.authmaster.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;

	public void register(RegisterDto req) {
		if (userRepository.existsByUsername(req.username())) {
			throw new IllegalArgumentException("Username is already in use");
		}
		if (userRepository.existsByEmail(req.email())) {
			throw new IllegalArgumentException("Email is already in use");
		}

		if (!passwordEncoder.matches(req.password(), passwordEncoder.encode(req.password()))) {
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

	public LoginSuccessResp login(LoginDto req) {
		User user = userRepository.findByEmail(req.email())
			.orElseThrow(() -> new IllegalArgumentException("해당 이메일과 일치 하는 유저 정보가 없습니다."));
		if(!passwordEncoder.matches(req.password(), passwordEncoder.encode(req.password()))) {
			throw new IllegalArgumentException("Password is incorrect");
		}
		String accessToken = jwtHelper.generateAccessToken(user);
		return new LoginSuccessResp(accessToken);
	}
}
