package com.veggiebasket.release_manager_backend.config;

import com.veggiebasket.release_manager_backend.entity.Developer;
import com.veggiebasket.release_manager_backend.repository.DeveloperRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Value("${app.developer.email:}")
    private String email;

    @Value("${app.developer.password:}")
    private String password;

    @Bean
    CommandLineRunner initializeDeveloper(
            DeveloperRepository developerRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (email == null || email.isBlank()
                    || password == null || password.isBlank()) {
                return;
            }

            if (developerRepository.findByEmailAndActiveTrue(email).isEmpty()) {

                Developer developer = new Developer();

                developer.setEmail(email);
                developer.setPassword(
                        passwordEncoder.encode(password)
                );
                developer.setActive(true);

                developerRepository.save(developer);
            }
        };
    }
}