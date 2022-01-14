package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;
import com.barisertakus.toyotamanport.dto.PlantDTO;
import com.barisertakus.toyotamanport.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAll")
    public ResponseEntity<Page<PlantDTO>> getAllByPagination(@RequestParam int pageNo, int pageSize, String sortType, String sortField){
        return ResponseEntity.ok(plantService.getAll(pageNo, pageSize, sortType, sortField));
    }

    @GetMapping("getAllPlants")
    public ResponseEntity<List<PlantDTO>> getAllPlants(){
        return ResponseEntity.ok(plantService.getAllPlants());
    }
}
