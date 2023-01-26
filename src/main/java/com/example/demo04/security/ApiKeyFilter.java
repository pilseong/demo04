package com.example.demo04.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo04.manager.ApiAuthenticationManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

  private final String key;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    ApiAuthenticationManager am = new ApiAuthenticationManager(key);
    
    String key = request.getHeader("x-api-key");
    if (key == null || "null".equals(key)) {
      filterChain.doFilter(request, response);
    }

    try {
      var authentication = new ApiKeyAuthentication(key);
      var result = am.authenticate(authentication);
      if (result.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(result);
        filterChain.doFilter(request, response);
      } else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } catch(Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

  }
}
