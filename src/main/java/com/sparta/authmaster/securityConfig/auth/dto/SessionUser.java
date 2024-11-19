package com.sparta.authmaster.securityConfig.auth.dto;

import java.io.Serializable;

import com.sparta.authmaster.entity.User;

import lombok.Getter;


// 인증된 사용자 정보
@Getter
public class SessionUser implements Serializable {
	private String username;
	private String email;

	public SessionUser(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
	}
}
