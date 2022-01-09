package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.JwtResponse;
import com.barisertakus.toyotamanport.dto.LoginRequest;
import com.barisertakus.toyotamanport.dto.SignupRequest;
import com.barisertakus.toyotamanport.dto.TokenDTO;
import com.barisertakus.toyotamanport.entity.Role;
import com.barisertakus.toyotamanport.entity.User;
import com.barisertakus.toyotamanport.repository.RoleRepository;
import com.barisertakus.toyotamanport.repository.UserRepository;
import com.barisertakus.toyotamanport.security.JwtUtils;
import com.barisertakus.toyotamanport.service.AuthService;
import com.barisertakus.toyotamanport.security.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(detail -> detail.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), jwt, roles);

    }

    @Override
    public ResponseEntity<?> register(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body("Error : Username is already in use!");
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body("Error : Email is already in use!");
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getName(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        if (role.isPresent()){
           roles.add(role.get());
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

    @Override
    public JwtResponse getUserDetailsFromToken(TokenDTO tokenDTO) {
        boolean isValid = jwtUtils.validateJwtToken(tokenDTO.getToken());
        if (isValid != true)
            throw new AccessDeniedException("Access Denied");

        String username = jwtUtils.getUserNameFromJwtToken(tokenDTO.getToken());
        if(username != null && !username.isEmpty()){
            User user = getUserByUsername(username);

            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toList());

            return new JwtResponse(user.getId() , username, user.getEmail(), tokenDTO.getToken(), roles);
        }
        log.error("Access Denied");
        throw new AccessDeniedException("Access Denied");
    }

    private User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        log.error("User not found with parameter {}", username);
        throw new IllegalArgumentException("User not found!");
    }
}
