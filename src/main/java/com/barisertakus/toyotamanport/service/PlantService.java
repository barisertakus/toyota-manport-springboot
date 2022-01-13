package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;

public interface PlantService {
    Boolean save(PlantCreateDTO plantDTO);
}
