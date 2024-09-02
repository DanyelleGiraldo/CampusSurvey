package com.campussurvey.campussurvey.User.security.Auth;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
     String username;
     String password;
     String address;
     private Set<String> roles = new HashSet<>(); 
     public Set<String> getRoles() {
         return roles != null ? roles : new HashSet<>();
     }
}
