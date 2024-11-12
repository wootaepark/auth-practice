package com.sparta.authmaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authmaster.authService.AuthService;
import com.sparta.authmaster.controller.dto.RegisterDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;


	@PostMapping("/register") // 등록
	public ResponseEntity<Void> register(@RequestBody RegisterDto req){
		authService.register(req);
		return ResponseEntity.ok().build();
	}

}
