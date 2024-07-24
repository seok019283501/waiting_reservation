package com.skshieldus.waiting_reservation_be.conifg;

import com.skshieldus.waiting_reservation_be.fillter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((auth)->{
                    auth.requestMatchers(
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/open-api/**").permitAll()
                            .anyRequest().authenticated();
                });
        httpSecurity.csrf((auth)->auth.disable());
        httpSecurity.formLogin((auth)->{
            auth.disable();
        });
        httpSecurity.httpBasic((auth)->{
            auth.disable();
        });
        httpSecurity.sessionManagement((auth)->{
            auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}