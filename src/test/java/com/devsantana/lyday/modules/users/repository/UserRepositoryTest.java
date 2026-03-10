package com.devsantana.lyday.modules.users.repository;

import com.devsantana.lyday.modules.users.model.Role;
import com.devsantana.lyday.modules.users.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UserRepositoryTest.TestAuditConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    @EnableJpaAuditing
    static class TestAuditConfig{
        @Bean
        public AuditorAware<String> auditorProvider(){
            return () -> Optional.of("test-user");
        }

    }
    @Test
    @DisplayName("Should save a user sucessfully")
    void shouldSaveUser(){
        User user = User.builder()
                .username("maria")
                .password("123456")
                .enabled(true)
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();
        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertNotNull(saved.getCreatedAt());
        assertEquals("test-user", saved.getCreatedBy());
    }
    @Test
    @DisplayName("Should find user by username")
    void shouldFindUserByUsername(){
        User user = User.builder()
                .username("joao")
                .password("123")
                .enabled(true)
                .roles(Set.of(Role.ROLE_USER))
                .build();

        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("joao");

        assertTrue(result.isPresent());
        assertEquals("joao", result.get().getUsername());
    }
    @Test
    @DisplayName("Should return true when username exists")
    void shouldReturnTrueWhenUsernameExists(){
        User user = User.builder()
                .username("admin")
                .password("123456")
                .enabled(true)
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("admin");

        assertTrue(exists);
    }
    @Test
    @DisplayName("Should return false when username does not exist")
    void shouldReturnFalseWhenUsernameDoesNotExist(){
        boolean exists = userRepository.existsByUsername("ghost");
        assertFalse(exists);
    }

}
