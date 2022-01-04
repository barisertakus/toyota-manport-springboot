package com.barisertakus.toyotamanport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private List<String> roles;
}
