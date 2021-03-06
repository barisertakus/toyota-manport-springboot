package com.barisertakus.toyotamanport.controller;

import com.barisertakus.toyotamanport.dto.ApplicationCreateDTO;
import com.barisertakus.toyotamanport.dto.ApplicationManagementDTO;
import com.barisertakus.toyotamanport.dto.ApplicationDashboardDTO;
import com.barisertakus.toyotamanport.dto.ApplicationViewDTO;
import com.barisertakus.toyotamanport.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/application")
@CrossOrigin
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<Page<ApplicationManagementDTO>> getAllByPagination(@RequestParam int pageNo, int pageSize, String sortType, String sortField){
        return ResponseEntity.ok(applicationService.getAll(pageNo, pageSize, sortType, sortField));
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody ApplicationCreateDTO applicationCreateDTO){
        return ResponseEntity.ok(applicationService.save(applicationCreateDTO));
    }

    @GetMapping("/getAllForDashboard")
    public ResponseEntity<List<ApplicationDashboardDTO>> getAllForDashboard(){
        return ResponseEntity.ok(applicationService.getAllForDashboard());
    }

    @GetMapping("/{shortName}")
    public ResponseEntity<ApplicationViewDTO> getByShortName(@PathVariable String shortName){
        return ResponseEntity.ok(applicationService.getByShortName(shortName));
    }

    @PutMapping("/edit")
    public ResponseEntity<Boolean> edit(@RequestBody ApplicationCreateDTO applicationCreateDTO){
        return ResponseEntity.ok(applicationService.edit(applicationCreateDTO));
    }
}
