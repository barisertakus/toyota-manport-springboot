package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.ApplicationCreateDTO;
import com.barisertakus.toyotamanport.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/application")
@CrossOrigin
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody ApplicationCreateDTO applicationCreateDTO){
        return ResponseEntity.ok(applicationService.save(applicationCreateDTO));
    }
}
