package com.sparta.authmaster.interceptor;

public class AuthException extends RuntimeException {
	public AuthException(String message) {
		super(message);
	}
}
