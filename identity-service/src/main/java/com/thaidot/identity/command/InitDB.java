package com.thaidot.identity.command;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.thaidot.identity.constant.PredefinedRole;
import com.thaidot.identity.entity.Role;
import com.thaidot.identity.entity.User;
import com.thaidot.identity.repository.RoleRepository;
import com.thaidot.identity.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB implements CommandLineRunner {

    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing application...");

        // Check if the admin user already exists
        if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
            // Create roles
            Role userRole = roleRepository.save(Role.builder()
                    .name(PredefinedRole.USER_ROLE)
                    .description("User role")
                    .build());

            Role adminRole = roleRepository.save(Role.builder()
                    .name(PredefinedRole.ADMIN_ROLE)
                    .description("Admin role")
                    .build());

            // Assign admin role to admin user
            var roles = new HashSet<Role>();
            roles.add(adminRole);

            // Create the admin user
            User adminUser = User.builder()
                    .username(ADMIN_USER_NAME)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .roles(roles)
                    .build();

            userRepository.save(adminUser);
            log.warn("Admin user has been created with default password: 'admin'. Please change it.");
        }

        log.info("Application initialization completed.");
    }
}