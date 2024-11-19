package com.sparta.authmaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authmaster.authService.AuthService;
import com.sparta.authmaster.controller.dto.LoginDto;
import com.sparta.authmaster.controller.dto.LoginSuccessResp;
import com.sparta.authmaster.controller.dto.RegisterDto;
import com.sparta.authmaster.controller.dto.VerifiedMember;
import com.sparta.authmaster.interceptor.Auth;

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


	@PostMapping("/login") // 로그인
	public ResponseEntity<LoginSuccessResp> login(@RequestBody LoginDto req){
		return ResponseEntity.ok().body(authService.login(req));
	}

	@GetMapping("/info") // 유저 정보 출력
	public ResponseEntity<Void> info(@AuthenticationPrincipal OAuth2User oAuth2User){
		String email = oAuth2User.getAttribute("email");
		System.out.println("email: " + email);;
		return ResponseEntity.ok().build();
	}

}
