package com.sparta.authmaster.resolver;

import java.util.Map;
import java.util.Objects;


import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sparta.authmaster.controller.dto.VerifiedMember;
import com.sparta.authmaster.entity.JoinPath;
import com.sparta.authmaster.interceptor.Auth;
import com.sparta.authmaster.interceptor.AuthException;
import com.sparta.authmaster.jwtHelper.JwtHelper;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerifiedMemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtHelper jwtHelper;

	@Override
	public VerifiedMember resolveArgument(
		MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
		NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory
	) {
		String accessToken = Objects.requireNonNull(nativeWebRequest.getHeader("Authorization"))
			.substring("Bearer ".length());
		Map<String, Object> info = jwtHelper.extractMemberInfo(accessToken);

		Long userId = Long.valueOf((String)info.get("subject"));
		String email = (String)info.get("email");
		String joinPath = (String)info.get("joinPath");

		return new VerifiedMember(userId, email, JoinPath.valueOf(joinPath));

	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
		boolean isAuthSuperType = parameter.getParameterType().equals(VerifiedMember.class);

		if (hasAuthAnnotation != isAuthSuperType) {
			throw new AuthException("@Auth 와 VerifiedMember 는 같이 쓰여야 합니다.");
		}

		return hasAuthAnnotation;

	}
}
