package com.sparta.authmaster.securityConfig.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sparta.authmaster.entity.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j(topic = "Security config")
public class SecurityConfig {


	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http,
		PrincipalOauth2UserService principalOauth2UserService) throws Exception {


		log.info("securityConfig 실행됨");
		http
			.csrf((csrf) -> csrf.disable())
			.headers(headers -> headers.frameOptions(frame -> frame.disable()))
			.authorizeHttpRequests(
				auth -> auth
					.requestMatchers("/loginForm").permitAll()
					.requestMatchers("/api/auth/info").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
					.anyRequest().authenticated()

			)
			.oauth2Login(form ->{
				form
					.loginPage("/loginForm")
					.userInfoEndpoint(userInfoEndpointConfig -> {
						userInfoEndpointConfig.userService(principalOauth2UserService);
					});
			})
			.formLogin(formLogin ->
				formLogin
					.loginPage("/loginForm")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/")

			)
			.logout(logout -> logout
				.logoutSuccessUrl("/")
			);

		return http.build();
	}
}
