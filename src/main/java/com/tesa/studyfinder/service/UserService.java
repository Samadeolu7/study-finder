package com.tesa.studyfinder.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tesa.studyfinder.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean register(String email, String rawPassword) {
        if (users.containsKey(email)) return false;
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        users.put(email, u);
        return true;
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        User u = users.get(email);
        if (u != null && passwordEncoder.matches(rawPassword, u.getPasswordHash())) {
            return Optional.of(u);
        }
        return Optional.empty();
    }
}
