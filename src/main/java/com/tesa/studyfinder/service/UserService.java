package com.tesa.studyfinder.service;

import com.tesa.studyfinder.model.User;
import com.tesa.studyfinder.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns false if email already exists
     */
    public boolean register(String email, String rawPassword) {
        if (userRepo.existsById(email)) return false;
        User u = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .build();
        userRepo.save(u);
        return true;
    }

    /**
     * Returns empty if no match
     */
    public Optional<User> authenticate(String email, String rawPassword) {
        return userRepo.findById(email)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPasswordHash()));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}