package com.campussurvey.campussurvey.User.security.Auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Role.domain.entities.Role;
import com.campussurvey.campussurvey.Role.infrastructure.RoleRepository;
import com.campussurvey.campussurvey.User.domain.entities.User;
import com.campussurvey.campussurvey.User.infrastructure.UserRepository;
import com.campussurvey.campussurvey.User.security.confi.jwt.JWTService;

import lombok.RequiredArgsConstructor;

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
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
    
        String token = jwtService.getToken(user);
    
        String role = user.getRoles().stream()
                           .findFirst()
                           .map(Role::getName)
                           .orElse("UNKNOWN_ROLE");
    
        return AuthResponse.builder()
            .token(token)
            .role(role)
            .build();
    }

    public AuthResponse register(LoginRequest request) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        System.out.println("Received registration request: " + request);
    
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Encode the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
    
        // Validate and assign roles
        Set<Role> userRoles = new HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            userRoles.add(role);
        }
    
        // Convert Set<Role> to List<Role>
        List<Role> rolesList = List.copyOf(userRoles);  // You can also use new ArrayList<>(userRoles);
    
        // Create and save the user
        User user = User.builder()
            .username(request.getUsername())
            .password(encodedPassword)
            .address(request.getAddress())
            .roles(rolesList)  // Now passing a List<Role>
            .enabled(true)
            .build();
    
        userRepository.save(user);
    
        // Generate and return the authentication token
        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }
    

}
