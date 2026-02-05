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

    // --------->ADMINUSER<----------
    private final String adminUsername;
    private final String adminPasswordHash;
    // --------->USERCOMUM<-----------
    private final String userUsername;
    private final String userPasswordHash;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;

        // --------->ADMINUSER<----------
        this.adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPasswordPlain = System.getenv("ADMIN_PASSWORD");

        // --------->USERCOMUM<-----------
        this.userUsername = System.getenv("USER_USERNAME");
        String userPasswordPlain = System.getenv("USER_PASSWORD");

        // --------->VERIFICA-VARIÁVEIS-DE-AMBIENTE<-----------
        if (adminUsername == null || adminPasswordPlain == null
                || userUsername == null || userPasswordPlain == null) {
            throw new IllegalStateException(
                    "Variáveis de ambiente ADMIN_USERNAME/ADMIN_PASSWORD ou " +
                            "USER_USERNAME/USER_PASSWORD não definidas"
            );
        }
        this.adminPasswordHash = passwordEncoder.encode(adminPasswordPlain);
        this.userPasswordHash = passwordEncoder.encode(userPasswordPlain);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if (username.equals(adminUsername)){
            return User.builder()
                    .username(adminUsername)
                    .password(adminPasswordHash)
                    .roles("ADMIN")
                    .build();
        }
        if (username.equals(userUsername)){
            return User.builder()
                    .username(userUsername)
                    .password(userPasswordHash)
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Nome de Usuario Não Encontrado");
    }
}
