package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.ApplicationPlantsAndInfrastructuresDTO;
import com.barisertakus.toyotamanport.dto.InfrastructureCreateDTO;
import com.barisertakus.toyotamanport.dto.PlantDTO;
import com.barisertakus.toyotamanport.dto.PlantWithTrackDTO;
import com.barisertakus.toyotamanport.entity.*;
import com.barisertakus.toyotamanport.repository.ApplicationPlantRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.InfrastructureService;
import com.barisertakus.toyotamanport.service.PlantService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApplicationPlantServiceImpl implements ApplicationPlantService {
    private final ApplicationPlantRepository applicationPlantRepository;
    private final PlantService plantService;
    private final InfrastructureService infrastructureService;
    private final ModelMapper modelMapper;

    public ApplicationPlantServiceImpl(ApplicationPlantRepository applicationPlantRepository, PlantService plantService, InfrastructureService infrastructureService, ModelMapper modelMapper) {
        this.applicationPlantRepository = applicationPlantRepository;
        this.plantService = plantService;
        this.infrastructureService = infrastructureService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ApplicationPlant> saveByApplication(Application application, List<PlantWithTrackDTO> plantDTOList, List<InfrastructureCreateDTO> infrastructureDTOList) {
        if (application != null) {
            List<Plant> plants = getPlantsByPlantDTOList(plantDTOList);
            ApplicationPlantsAndInfrastructuresDTO applicationPlantsAndInfrastructures =
                    generateApplicationPlantsAndInfrastructures(application, plantDTOList, plants, infrastructureDTOList);
            List<ApplicationPlant> applicationPlants = applicationPlantsAndInfrastructures.getApplicationPlants();
            List<Infrastructure> infrastructures = applicationPlantsAndInfrastructures.getInfrastructures();
            infrastructureService.saveAll(infrastructures);
            return applicationPlantRepository.saveAll(applicationPlants);
        }
        log.error("Application record couldn't be found!");
        throw new IllegalArgumentException("Application record couldn't be found!");
    }

    private ApplicationPlantsAndInfrastructuresDTO generateApplicationPlantsAndInfrastructures(
            Application application, List<PlantWithTrackDTO> plantDTOList, List<Plant> plants, List<InfrastructureCreateDTO> infrastructureDTOList) {
        List<Infrastructure> infrastructures = new ArrayList<>();
        List<ApplicationPlant> applicationPlants = plantDTOList.stream().map(plantDTO -> {
            Boolean track = plantDTO.getTrack(); // application - country track.
            Plant plant = getPlantFromPlantListById(plantDTO.getId(), plants);
            Infrastructure infrastructure = getInfrastructureByCountry(plant.getCountry(), infrastructureDTOList);
            if(infrastructure != null)
                infrastructures.add(infrastructure);
            ApplicationPlant applicationPlant = new ApplicationPlant(track, application, plant, infrastructure);
            addApplicationPlantToObjects(applicationPlant, application, plant, infrastructure);
            return applicationPlant;
        }).collect(Collectors.toList());
        return new ApplicationPlantsAndInfrastructuresDTO(applicationPlants, infrastructures);
    }

    private List<Plant> getPlantsByPlantDTOList(List<PlantWithTrackDTO> plantDTOList) {
        List<Long> plantIdList = getPlantIdList(plantDTOList);
        List<Plant> plants = plantService.findByIdIn(plantIdList);
        return plants;
    }

    private List<Long> getPlantIdList(List<PlantWithTrackDTO> plantDTOList) {
        return plantDTOList.stream().map(PlantWithTrackDTO::getId).collect(Collectors.toList());
    }

    private Plant getPlantFromPlantListById(Long id, List<Plant> plants) {
        Optional<Plant> plantOptional = plants.stream()
                .filter(plant -> Objects.equals(plant.getId(), id)).findAny();
        if (plantOptional.isPresent()) {
            return plantOptional.get();
        }
        log.error("Plant record couldn't be found with parameter id : {} !", id);
        throw new IllegalArgumentException("Plant record couldn't be found!");
    }

    private Infrastructure getInfrastructureByCountry(String country, List<InfrastructureCreateDTO> infrastructures) {
        Optional<InfrastructureCreateDTO> infrastructureOpt = infrastructures.stream()
                .filter(infrastructure -> Objects.equals(infrastructure.getCountry(), country)).findAny();
        if (infrastructureOpt.isPresent()) {
            return modelMapper.map(infrastructureOpt.get(), Infrastructure.class);
        }
        return null;
    }

    private void addApplicationPlantToObjects(ApplicationPlant applicationPlant, Application application, Plant plant, Infrastructure infrastructure) {
        application.getApplicationPlants().add(applicationPlant);
        plant.getApplicationPlants().add(applicationPlant);
        if(infrastructure != null)
            infrastructure.getApplicationPlants().add(applicationPlant);
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
        if (applicationPlant.isPresent()) {
            return applicationPlant.get();
        }
        log.error("Application and plant record could not be found. applicationPlantId {}", applicationPlantId);
        throw new IllegalArgumentException("Application and Plant record could not be found.");
    }
}
