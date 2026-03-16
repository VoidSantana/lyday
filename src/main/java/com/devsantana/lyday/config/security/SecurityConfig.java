package com.devsantana.lyday.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                                //=============================================================
                                // Paginas Frontend
                                .requestMatchers("/ui/**").permitAll()
                                // Arquivos estáticos
                                .requestMatchers("/js/**", "/css/**").permitAll()
                                //=============================================================

                                //=============================================================
                                //== LOGIN LIVRE

                                .requestMatchers("/auth/**", "/error").permitAll()
                                //=============================================================

                                //=============================================================
                                //==ADMIN e USER
                                .requestMatchers(HttpMethod.GET, "/api/products/**")
                                .hasAnyRole("ADMIN", "USER")
                                //=============================================================


                                //=============================================================
                                // ===APENAS ADMIN
                                .requestMatchers("/api/users/**").hasRole("ADMIN")
                                //------------------------------PRODUCTS------------------------------------------------
                                .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                                //------------------------------INVENTORY------------------------------------------------
                                .requestMatchers(HttpMethod.POST, "/api/inventory/stock/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/inventory/stock/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/inventory/stock/**").hasRole("ADMIN")
                                //------------------------------BRANCHES------------------------------------------------
                                .requestMatchers(HttpMethod.POST, "/api/company/branches/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/company/branches/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/company/branches/**").hasRole("ADMIN")
                                //------------------------------WAREHOUSES----------------------------------------------
                                .requestMatchers(HttpMethod.POST, "/api/company/warehouses/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/company/warehouses/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/company/warehouses/**").hasRole("ADMIN")
                                //-------------------------------LOCATION-----------------------------------------------
                                .requestMatchers(HttpMethod.POST, "/api/company/locations/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/company/locations/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/company/locations/**").hasRole("ADMIN")
                                //==DEMAIS, OBRIGATORIEDADE ESTAR LOGADO

                                .anyRequest().authenticated()
                                //=============================================================
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}