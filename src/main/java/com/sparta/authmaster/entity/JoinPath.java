package com.sparta.authmaster.entity;

import lombok.Getter;

@Getter
public enum JoinPath {
	KAKAO("KAKAO"),
	BASIC("BASIC");

	private final String joinPathValue;

	JoinPath(String joinPathValue){
		this.joinPathValue = joinPathValue;
	}
}
