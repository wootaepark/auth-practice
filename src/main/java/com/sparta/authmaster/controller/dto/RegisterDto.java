package com.sparta.authmaster.controller.dto;

import com.sparta.authmaster.entity.JoinPath;


public record RegisterDto(
	String username,
	String email,
	String password,
	String passwordConfirm,
	JoinPath joinPath) {

}
