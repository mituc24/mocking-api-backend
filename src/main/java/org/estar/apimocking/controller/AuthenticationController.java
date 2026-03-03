package org.estar.apimocking.controller;

import jakarta.validation.Valid;
import org.estar.apimocking.dtos.request.LoginRequest;
import org.estar.apimocking.dtos.request.SignupRequest;
import org.estar.apimocking.dtos.response.AuthenticationResponse;
import org.estar.apimocking.dtos.response.ResponseUser;
import org.estar.apimocking.exceptions.ResourceAlreadyExistException;
import org.estar.apimocking.models.Role;
import org.estar.apimocking.models.User;
import org.estar.apimocking.repository.UserRepository;
import org.estar.apimocking.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request)
    {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        AuthenticationResponse authResponse = new AuthenticationResponse(jwtService.generateToken(user),new ResponseUser(user));
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody @Valid SignupRequest request)
    {
        if(userRepository.existsByUsername(request.getUsername()))
        {
            throw new ResourceAlreadyExistException("Username is exist, try another one");
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setCreatedAt(new Date());
        newUser.setRole(Role.USER);

        newUser = userRepository.save(newUser);
        AuthenticationResponse authResponse = new
                AuthenticationResponse(jwtService.generateToken(newUser), new ResponseUser(newUser));
        return ResponseEntity.ok(authResponse);
    }
}
