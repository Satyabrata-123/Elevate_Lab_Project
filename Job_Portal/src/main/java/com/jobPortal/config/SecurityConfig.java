package com.jobPortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth

                // ✅ Public endpoints (no auth required)
                .requestMatchers(
                    "/api/User/**",               // All User endpoints (like register, getUserByUserName)
                    "/api/Job/jobs",              // GET all jobs
                    "/api/Job/jobs/**"            // GET job by ID, employer, etc.
                ).permitAll()

                // 🔐 Protected endpoints (need authentication)
                .requestMatchers(
                    "/api/Job/jobs",              // POST to create job (same as above GET, so needs attention)
                    "/api/Application/**"         // Applications (apply, fetch)
                ).authenticated()

                // Catch all - protect anything else
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}
