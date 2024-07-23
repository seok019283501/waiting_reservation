package com.skshieldus.waiting_reservation_be.conifg;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((auth)->{
                    auth.requestMatchers("/open-api/**").permitAll()
                            .requestMatchers("/api/admin").hasRole("ADMIN")
                            .anyRequest().authenticated();
                });
        httpSecurity.csrf((auth)->auth.disable());
        return httpSecurity.build();
    }
}