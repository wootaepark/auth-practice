package com.sparta.authmaster.securityConfig.auth.dto;

import java.util.Map;

import com.sparta.authmaster.entity.JoinPath;
import com.sparta.authmaster.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String username;
	private String email;
	private String picture;

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.username((String) attributes.get("username"))
			.email((String) attributes.get("email"))
			.picture((String)attributes.get("picture"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public User toEntity(){
		return User.builder()
			.username(username)
			.email(email)
			.joinPath(JoinPath.GOOGLE)
			.build();

	}


}
