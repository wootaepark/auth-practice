package com.sparta.authmaster.jwtHelper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sparta.authmaster.entity.JoinPath;
import com.sparta.authmaster.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHelper {

	@Value("${jwt.token.secret.key}")
	private String secretKey;

	@Value("${jwt.token.expires.in}")
	private int expireIn;
	private Key key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(User user) {
		return Jwts.builder()
			.setSubject(user.getId().toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.claim("email", user.getEmail())
			.claim("JoinPath", user.getJoinPath())
			.setExpiration(new Date(System.currentTimeMillis() + expireIn * 1000L))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

	}

	public void validateToken(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
		} catch (JwtException e) {
			throw new JwtException(e.getMessage());
		}
	}

	public Map<String, Object> extractMemberInfo(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			String username = claims.get("email", String.class);
			String joinPath = claims.get("JoinPath", String.class);

			System.out.println("email: " + username);
			System.out.println("joinPath: " + joinPath);

			Map<String, Object> map = new HashMap<>();
			map.put("subject", claims.getSubject());
			map.put("email", claims.get("email", String.class));
			map.put("joinPath", claims.get("JoinPath", String.class));

			return map;

		} catch (JwtException e) {
			throw new JwtException(e.getMessage());
		}
	}

}
