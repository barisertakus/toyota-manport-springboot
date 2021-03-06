package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.PlantCreateDTO;
import com.barisertakus.toyotamanport.dto.PlantDTO;
import com.barisertakus.toyotamanport.entity.Plant;
import com.barisertakus.toyotamanport.repository.PlantRepository;
import com.barisertakus.toyotamanport.service.PlantService;
import com.barisertakus.toyotamanport.service.ServerService;
import com.barisertakus.toyotamanport.utils.CreatePageable;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;
    private final ServerService serverService;
    private final ModelMapper modelMapper;

    public PlantServiceImpl(PlantRepository plantRepository, ServerService serverService, ModelMapper modelMapper) {
        this.plantRepository = plantRepository;
        this.serverService = serverService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean save(PlantCreateDTO plantDTO) {
        boolean exist = plantRepository.existsByShortCodeOrFullName(plantDTO.getShortCode(), plantDTO.getFullName());
        if(!exist){
            Plant plant = modelMapper.map(plantDTO, Plant.class);
            plantRepository.save(plant);
            serverService.addServersToPlant(plant);
            return true;
        }
        throw new IllegalArgumentException("ShortCode or FullName already exists!");
    }

    @Override
    public Page<PlantDTO> getAll(int pageNo, int pageSize, String sortType, String sortField) {
        Pageable pageable = CreatePageable.create(pageNo, pageSize, sortType, sortField);
        Page<Plant> plants = plantRepository.findByIsActiveTrue(pageable);
        return plants.map(plant -> modelMapper.map(plant, PlantDTO.class));
    }

    @Override
    public List<PlantDTO> getAllPlants() {
        List<Plant> plants = plantRepository.getAllByIsActiveTrue();
        return modelMapper.map(plants, new TypeToken<List<PlantDTO>>(){}.getType());
    }

    @Override
    public List<Plant> findByIdIn(List<Long> plantIdList) {
        List<Plant> plants = plantRepository.findByIdIn(plantIdList);
        return plants;
    }
}
