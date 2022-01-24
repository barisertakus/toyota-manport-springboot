package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.PlantDTO;
import com.barisertakus.toyotamanport.dto.PlantWithTrackDTO;
import com.barisertakus.toyotamanport.entity.*;
import com.barisertakus.toyotamanport.repository.ApplicationPlantRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.PlantService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApplicationPlantServiceImpl implements ApplicationPlantService {
    private final ApplicationPlantRepository applicationPlantRepository;
    private final PlantService plantService;
    private final ModelMapper modelMapper;

    public ApplicationPlantServiceImpl(ApplicationPlantRepository applicationPlantRepository, PlantService plantService, ModelMapper modelMapper) {
        this.applicationPlantRepository = applicationPlantRepository;
        this.plantService = plantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ApplicationPlant> saveByApplicationAndPlantList(Application application, List<PlantWithTrackDTO> plantDTOList){
        if(application != null){
            List<Plant> plants = getPlantsByPlantDTOList(plantDTOList);
            List<ApplicationPlant> applicationPlants = generateApplicationPlants(application, plantDTOList, plants);
            return applicationPlantRepository.saveAll(applicationPlants);
        }
        log.error("Application record couldn't be found!");
        throw new IllegalArgumentException("Application record couldn't be found!");
    }

    private List<ApplicationPlant> generateApplicationPlants(Application application, List<PlantWithTrackDTO> plantDTOList, List<Plant> plants){
        List<ApplicationPlant> applicationPlants = plantDTOList.stream().map(plantDTO -> {
            Boolean track = plantDTO.getTrack(); // application - country track.
            Plant plant = getPlantFromPlantListById(plantDTO.getId(), plants);
            ApplicationPlant applicationPlant = new ApplicationPlant(track, application, plant);
            addApplicationPlantToObjects(applicationPlant, application, plant);
            return applicationPlant;
        }).collect(Collectors.toList());
        return applicationPlants;
    }

    private List<Plant> getPlantsByPlantDTOList(List<PlantWithTrackDTO> plantDTOList){
        List<Long> plantIdList = getPlantIdList(plantDTOList);
        List<Plant> plants = plantService.findByIdIn(plantIdList);
        return plants;
    }

    private List<Long> getPlantIdList(List<PlantWithTrackDTO> plantDTOList){
        return plantDTOList.stream().map(PlantWithTrackDTO::getId).collect(Collectors.toList());
    }

    private Plant getPlantFromPlantListById(Long id, List<Plant> plants){
        Optional<Plant> plantOptional = plants.stream()
                .filter(plant -> Objects.equals(plant.getId(), id)).findAny();
        if(plantOptional.isPresent()){
            return plantOptional.get();
        }
        log.error("Plant record couldn't be found with parameter id : {} !",id);
        throw new IllegalArgumentException("Plant record couldn't be found!");
    }

    private void addApplicationPlantToObjects(ApplicationPlant applicationPlant, Application application, Plant plant){
        application.getApplicationPlants().add(applicationPlant);
        plant.getApplicationPlants().add(applicationPlant);
    }

    @Override
    public ApplicationPlant findByApplicationIdAndPlantId(Long applicationId, Long plantId) {
        Optional<ApplicationPlant> applicationPlant = applicationPlantRepository.findByApplicationIdAndPlantId(applicationId, plantId);
        if (applicationPlant.isPresent()) {
            return applicationPlant.get();
        }
        log.error("Application and plant relationship could not be found. applicationId : {}, plantId : {}", applicationId, plantId);
        throw new IllegalArgumentException("Application and Plant relationship could not be found.");
    }

    @Override
    public ApplicationPlant findById(Long applicationPlantId) {
        Optional<ApplicationPlant> applicationPlant = applicationPlantRepository.findById(applicationPlantId);
        if(applicationPlant.isPresent()){
            return applicationPlant.get();
        }
        log.error("Application and plant record could not be found. applicationPlantId {}", applicationPlantId);
        throw new IllegalArgumentException("Application and Plant record could not be found.");
    }
}
