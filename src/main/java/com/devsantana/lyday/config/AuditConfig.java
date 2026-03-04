package com.devsantana.lyday.config;

import com.devsantana.lyday.shared.audit.SecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
public class AuditConfig {

    @Bean
    public SecurityAuditorAware securityAuditorAware(){
        return new SecurityAuditorAware();
    }
}
