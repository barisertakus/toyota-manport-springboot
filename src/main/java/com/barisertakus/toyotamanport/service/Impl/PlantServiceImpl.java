package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;
import com.barisertakus.toyotamanport.entity.Plant;
import com.barisertakus.toyotamanport.repository.PlantRepository;
import com.barisertakus.toyotamanport.service.PlantService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;
    private final ModelMapper modelMapper;

    public PlantServiceImpl(PlantRepository plantRepository, ModelMapper modelMapper) {
        this.plantRepository = plantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean save(PlantCreateDTO plantDTO) {
        boolean exist = plantRepository.existsByShortCodeOrFullName(plantDTO.getShortCode(), plantDTO.getFullName());
        if(!exist){
            Plant plant = modelMapper.map(plantDTO, Plant.class);
            plantRepository.save(plant);
            return true;
        }
        throw new IllegalArgumentException("ShortCode or FullName already exists!");
    }
}
