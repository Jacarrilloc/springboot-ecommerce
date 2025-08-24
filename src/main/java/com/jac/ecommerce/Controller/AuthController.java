package com.jac.ecommerce.controller;

import com.jac.ecommerce.dto.AuthResponse;
import com.jac.ecommerce.dto.LoginRequest;
import com.jac.ecommerce.model.User;
import com.jac.ecommerce.repository.UserRepository;
import com.jac.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getUserName());
        return new AuthResponse(token);
    }
}
