package com.sparta.authmaster.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sparta.authmaster.interceptor.JwtValidationInterceptor;
import com.sparta.authmaster.resolver.VerifiedMemberArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtValidationInterceptor jwtValidationInterceptor;
	private final VerifiedMemberArgumentResolver verifiedMemberArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtValidationInterceptor)
			//.excludePathPatterns("/api/auth/**", "/error");
			.excludePathPatterns("/**"); // oauth 를 위한 임시 해제
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
		resolvers.add(verifiedMemberArgumentResolver);
	}
}
