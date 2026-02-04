package com.devsantana.lyday.modules.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = System.getenv("ADMIN_PASSWORD");

        String userUsername = System.getenv("USER_USERNAME");
        String userPassword = System.getenv("USER_PASSWORD");

        if (adminUsername == null || adminPassword == null || username == null || userPassword == null) {
            throw new IllegalStateException(
                    "Variáveis de ambiente ADMIN_USERNAME/ADMIN_PASSWORD ou " +
                            "USER_USERNAME/USER_PASSWORD não definidas"
            );
        }
        if (username.equals(adminUsername)){
            return User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles("ADMIN")
                    .build();
        }
        if (username.equals(userUsername)){
            return User.builder()
                    .username(userUsername)
                    .password(passwordEncoder.encode(userPassword))
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Nome de Usuario Não Encontrado");
    }
}
