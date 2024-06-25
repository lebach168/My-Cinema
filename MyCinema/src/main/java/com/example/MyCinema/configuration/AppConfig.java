package com.example.MyCinema.configuration;

import com.example.MyCinema.model.Staff;
import com.example.MyCinema.repository.StaffRepository;
import com.example.MyCinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AppConfig {

    private final PasswordEncoder passwordEncoder;


    static final String ADMIN_USER_NAME = "admin";
    static final String ADMIN_PASSWORD = "123456";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner initApplicationRunner(StaffRepository staffRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (staffRepository.findByName(ADMIN_USER_NAME).isEmpty()) {
                Staff admin = Staff.builder()
                        .name(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role("ADMIN")
                        .build();
               staffRepository.save(admin);
                log.warn("admin user has been created with default password: 123456, please change it");
            }
            log.info("Application initialization completed!");
        };
    }

}
