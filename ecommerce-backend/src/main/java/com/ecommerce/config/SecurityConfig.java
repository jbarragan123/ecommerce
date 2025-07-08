package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()  // permite acceso libre a H2 Console
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())  // desactiva CSRF para pruebas locales
                .headers(headers -> headers.disable());  // desactiva todos los headers de seguridad

        return http.build();
    }
}