package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;
import com.barisertakus.toyotamanport.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/plant")
@CrossOrigin
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody PlantCreateDTO plantCreateDTO){
        return ResponseEntity.ok(plantService.save(plantCreateDTO));
    }
}
