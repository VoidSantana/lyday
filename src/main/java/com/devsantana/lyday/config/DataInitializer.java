package com.devsantana.lyday.config;

import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import com.devsantana.lyday.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedAdmin(){
        return  args -> {

            // ========ADMIN INICIAL DO SISTEMA=========
            String adminUsername = System.getenv("ADMIN_USERNAME");
            String adminPassword = System.getenv("ADMIN_PASSWORD");

            // Se Não tiver variáveis de ambiente você pode colocar default
            // (para devs). Em PROD, sempre usar env.
            if ( adminUsername == null) adminUsername = "admin";
            if ( adminPassword == null) adminPassword = "123456";
            // Se Já existir, não faz nada
            if (userRepository.existsByUsername(adminUsername)){
                System.out.println(" Admin já existe: " + adminUsername);
                return;
            }

            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .enabled(true)
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .build();

            userRepository.save(admin);

            System.out.println(" Admin criado: " + adminUsername);
        };
    }
}