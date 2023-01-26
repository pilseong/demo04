package com.example.demo04.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.demo04.security.ApiKeyAuthentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiKeyProvider implements AuthenticationProvider {

  private final String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ApiKeyAuthentication auth = (ApiKeyAuthentication) authentication;
    if (auth.getKey().equals(key)) {
      auth.setAuthenticated(true);
      return auth;
    }

    throw new BadCredentialsException("not matched");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthentication.class.equals(authentication);
  }
  
}
