package com.sparta.authmaster.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderTokenValidationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
		throws IOException, ServletException {
		if (this.isApplicable(req)) {
			chain.doFilter(req, res);
			return;
			// 인증 제외 URI 로는 다음 필터로 진행 가능
		}

		String accessToken = Optional.ofNullable(req.getHeader("Authorization"))
			.map(header -> header.substring("Bearer ".length()))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."));


		chain.doFilter(req, res); // 토큰이 존재하면 다음으로 이동

	}

	public boolean isApplicable(HttpServletRequest req) {
		return req.getRequestURI().startsWith("/api/auth") ||
			req.getRequestURI().startsWith("/auth/oauth") ||
			req.getRequestURI().startsWith("/favicon");
	}
}
