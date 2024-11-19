package com.sparta.authmaster.securityConfig.auth;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sparta.authmaster.entity.User;
import com.sparta.authmaster.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username = " + username);
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null){
			return new UserDetailsImpl (userEntity);
		}

		return null;
	}
}
