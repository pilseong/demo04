package com.example.demo04.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.demo04.provider.ApiKeyProvider;
import com.example.demo04.security.ApiKeyAuthentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAuthenticationManager implements AuthenticationManager {

	private final String key;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		ApiKeyProvider ap = new ApiKeyProvider(key);
		
		if (ap.supports(authentication.getClass())) {
			var result = ap.authenticate(authentication);
			if (result.isAuthenticated()) {
				return result;
			}

			throw new BadCredentialsException("not matched");
		}

		return authentication;
	}
  
}
