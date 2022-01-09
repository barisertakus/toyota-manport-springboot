package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.LoginRequest;
import com.barisertakus.toyotamanport.dto.SignupRequest;
import com.barisertakus.toyotamanport.dto.TokenDTO;
import com.barisertakus.toyotamanport.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest){
        return authService.register(signupRequest);
    }

    @PostMapping("/getUserFromToken")
    public ResponseEntity<?> getUserFromToken(@RequestBody TokenDTO tokenDTO){
        return ResponseEntity.ok( authService.getUserDetailsFromToken(tokenDTO));
    }

}
