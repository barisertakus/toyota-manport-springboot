package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;
import com.barisertakus.toyotamanport.dto.PlantDTO;
import com.barisertakus.toyotamanport.entity.Plant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlantService {
    Boolean save(PlantCreateDTO plantDTO);

    Page<PlantDTO> getAll(int pageNo, int pageSize, String sortType, String sortField);

    List<PlantDTO> getAllPlants();

    List<Plant> findByIdIn(List<Long> plantIdList);
}
