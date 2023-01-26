package com.example.demo04.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo04.security.ApiKeyFilter;

@Configuration
public class SecurityConfig {
  
  @Value("${secret.key}")
  private String key;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.httpBasic();

    http.addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class);

    http.authorizeHttpRequests().anyRequest().authenticated();

    return http.build();
  }


}
