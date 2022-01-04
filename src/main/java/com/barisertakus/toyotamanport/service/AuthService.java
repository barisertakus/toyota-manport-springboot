package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.JwtResponse;
import com.barisertakus.toyotamanport.dto.LoginRequest;
import com.barisertakus.toyotamanport.dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    JwtResponse authenticate(LoginRequest loginRequest);

    ResponseEntity<?> register(SignupRequest signupRequest);
}
