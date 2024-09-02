package com.campussurvey.campussurvey.User.security.Auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.User.domain.entities.User;
import com.campussurvey.campussurvey.User.infrastructure.UserRepository;
import com.campussurvey.campussurvey.User.security.confi.jwt.JWTService;
import com.campussurvey.campussurvey.Role.domain.entities.Role;
import com.campussurvey.campussurvey.Role.infrastructure.RoleRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;  
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(LoginRequest request) {
        // Depuración
        System.out.println("Received registration request: " + request);
    
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(request.getPassword());
    
        // Validar y asignar roles
        Set<Role> userRoles = new HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                userRoles.add(role);
            } else {
                throw new RuntimeException("Role not found: " + roleName);
            }
        }
    
        // Crear y guardar el usuario
        User user = User.builder()
            .username(request.getUsername())
            .password(encodedPassword)
            .address(request.getAddress())
            .roles(userRoles)
            .enabled(true)
            .build();
    
        userRepository.save(user);
    
        // Generar y devolver el token de autenticación
        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }
}
