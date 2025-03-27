package com.shortty.backend.controller;

import com.shortty.backend.dtos.LoginRequest;
import com.shortty.backend.dtos.RegisterRequest;
import com.shortty.backend.models.User;
import com.shortty.backend.security.jwt.JwtAuthenticationResponse;
import com.shortty.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setRole("ROLE_USER");
            user.setPassword(registerRequest.getPassword());
            User isUserRegister = userService.registerUser(user);
            if (isUserRegister == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.ok("User register successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            JwtAuthenticationResponse jwtToken = userService.authenticateUser(loginRequest);
            if (jwtToken == null || jwtToken.getJwtToken().equals("BAD_CREDENTIALS")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
