package com.barisertakus.toyotamanport.config;

import com.barisertakus.toyotamanport.security.service.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetailsImpl){
                String username = ((UserDetailsImpl) principal).getUsername();
                return Optional.of(username);
            }
            return Optional.empty();
        };
    }
}
