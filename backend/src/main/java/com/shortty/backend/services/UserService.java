package com.shortty.backend.services;

import com.shortty.backend.dtos.LoginRequest;
import com.shortty.backend.models.User;
import com.shortty.backend.repository.UserRepository;
import com.shortty.backend.security.jwt.JwtAuthenticationResponse;
import com.shortty.backend.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    // signup user service
    public User registerUser(User user) {
        boolean isUserAlreadyPresent = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (isUserAlreadyPresent)   return null;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // login user service
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        if (!authentication.isAuthenticated())  new JwtAuthenticationResponse("BAD_CREDENTIALS");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }
}
