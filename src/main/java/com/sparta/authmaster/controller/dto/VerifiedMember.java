package com.sparta.authmaster.controller.dto;

import com.sparta.authmaster.entity.JoinPath;

public record VerifiedMember(
	Long id, String email, JoinPath joinPath
) {
}
