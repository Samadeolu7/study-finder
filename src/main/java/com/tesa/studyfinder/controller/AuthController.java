package com.tesa.studyfinder.controller;

import com.tesa.studyfinder.dto.AuthRequest;
import com.tesa.studyfinder.dto.AuthResponse;
import com.tesa.studyfinder.security.JwtUtil;
import com.tesa.studyfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthRequest req) {
        boolean ok = userService.register(req.getEmail(), req.getPassword());
        return ok
                ? ResponseEntity.ok("User registered")
                : ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody AuthRequest req) {
        return userService.authenticate(req.getEmail(), req.getPassword())
                .map(u -> {
                    String token = jwtUtil.generateToken(u.getEmail());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(Authentication auth) {
        // If we reach here, the JWT was valid and Spring Security populated `auth`
        return ResponseEntity.ok(auth.getName());  // returns the email
    }
}

