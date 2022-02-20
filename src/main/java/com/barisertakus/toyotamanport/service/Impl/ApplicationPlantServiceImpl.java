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

import java.util.*;
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
            if (applicationPlants.isEmpty()) {
                return Collections.emptyList();
            }
            infrastructureService.saveAll(infrastructures);
            return applicationPlantRepository.saveAll(applicationPlants);
        }
        log.error("Application record couldn't be found!");
        throw new IllegalArgumentException("Application record couldn't be found!");
    }

    private ApplicationPlantsAndInfrastructuresDTO generateApplicationPlantsAndInfrastructures(
            Application application, List<PlantWithTrackDTO> plantDTOList,
            List<Plant> plants, List<InfrastructureCreateDTO> infrastructureDTOList
    ) {
        List<Infrastructure> infrastructures = new ArrayList<>();
        List<ApplicationPlant> applicationPlants = plantDTOList.stream().map(plantDTO -> {
            Boolean track = plantDTO.getTrack(); // application - country track.
            Plant plant = getPlantFromPlantListById(plantDTO.getId(), plants);
            Infrastructure infrastructure = getInfrastructureByCountry(plant.getCountry(), infrastructureDTOList);
            if (infrastructure != null)
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

    public ApplicationPlant generateApplicationPlant(PlantWithTrackDTO plantDTO, List<Plant> plants,
                                                     Application application, Infrastructure infrastructure){
        Plant plant = getPlantFromPlantListById(plantDTO.getId(), plants);
        ApplicationPlant newApplicationPlant = new ApplicationPlant(plantDTO.getTrack(), application, plant, infrastructure);
        addApplicationPlantToObjects(newApplicationPlant, application, plant, infrastructure);
        return newApplicationPlant;
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
        if (infrastructure != null)
            infrastructure.getApplicationPlants().add(applicationPlant);
    }

    @Override
    public List<ApplicationPlant> editApplicationPlants(Application application, List<PlantWithTrackDTO> plantDTOList,
                                                        List<InfrastructureCreateDTO> infrastructureDTOList){
        ApplicationPlantsAndInfrastructuresDTO applicationPlantDetails = editApplicationPlantDetails(application, plantDTOList, infrastructureDTOList);
        List<ApplicationPlant> applicationPlants = applicationPlantDetails.getApplicationPlants();
        List<Infrastructure> infrastructures = applicationPlantDetails.getInfrastructures();
        application.setApplicationPlants(applicationPlants);
        infrastructureService.saveAll(infrastructures);
        return applicationPlantRepository.saveAll(applicationPlants);
    }

    private ApplicationPlantsAndInfrastructuresDTO editApplicationPlantDetails(Application application, List<PlantWithTrackDTO> plantDTOList,
                                                                               List<InfrastructureCreateDTO> infrastructureDTOList){
        List<ApplicationPlant> applicationPlants = application.getApplicationPlants();
        List<Infrastructure> infrastructures = new ArrayList<>();
        List<Long> deleteInfrastructureIdList = new ArrayList<>();
        List<Plant> plants = getPlantsByPlantDTOList(plantDTOList);
        List<ApplicationPlant> applicationPlantList = plantDTOList.stream().map(plantDTO -> {
            ApplicationPlant applicationPlant = checkApplicationPlantWithPlantId(applicationPlants, plantDTO.getId());
            Infrastructure infrastructure = getInfrastructureByCountry(plantDTO.getCountry(), infrastructureDTOList);
            if(infrastructure != null)
                infrastructures.add(infrastructure);
            if (applicationPlant != null) {
                return editExistingApplicationPlant(applicationPlant, infrastructure, plantDTO,deleteInfrastructureIdList);
            } else {
                return generateApplicationPlant(plantDTO, plants, application, infrastructure);
            }
        }).collect(Collectors.toList());

        deleteRemovedPlants(applicationPlants, plantDTOList);
        infrastructureService.deleteAllById(deleteInfrastructureIdList);
        return new ApplicationPlantsAndInfrastructuresDTO(applicationPlantList, infrastructures);
    }

    public ApplicationPlant editExistingApplicationPlant(ApplicationPlant applicationPlant, Infrastructure infrastructure,
                                                         PlantWithTrackDTO plantDTO, List<Long> idList){
        applicationPlant.setTrack(plantDTO.getTrack());

        if(applicationPlant.getInfrastructure() != null && infrastructure == null){ // infrastructure has been removed from applicationPlant
            idList.add(applicationPlant.getInfrastructure().getId());
        }
        applicationPlant.setInfrastructure(infrastructure);
        return applicationPlant;
    }

    private void deleteRemovedPlants(List<ApplicationPlant> applicationPlants, List<PlantWithTrackDTO> plants){
        List<ApplicationPlant> applicationPlantsDelete = new ArrayList<>();
        applicationPlants.forEach(applicationPlant -> {
            if(!checkApplicationPlantInPlants(applicationPlant, plants))
                applicationPlantsDelete.add(applicationPlant);
        });
        if(!applicationPlantsDelete.isEmpty())
            applicationPlantRepository.deleteAll(applicationPlantsDelete);
    }

    private Boolean checkApplicationPlantInPlants(ApplicationPlant applicationPlant, List<PlantWithTrackDTO> plants){
        Optional<PlantWithTrackDTO> plant = plants.stream()
                .filter(plantDTO -> applicationPlant.getPlant().getId().equals(plantDTO.getId())).findAny();
        return plant.isPresent();
    }

    private ApplicationPlant checkApplicationPlantWithPlantId(List<ApplicationPlant> applicationPlants, Long plantId){
        Optional<ApplicationPlant> applicationPlantOptional = applicationPlants.stream()
                .filter(applicationPlant -> applicationPlant.getPlant().getId().equals(plantId)).findAny();
        return applicationPlantOptional.orElse(null);
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

    @Override

    public ApplicationPlant getApplicationPlantByCountry(String country, List<ApplicationPlant> applicationPlants) {
        Optional<ApplicationPlant> applicationPlantOpt = applicationPlants.stream().filter(applicationPlant -> applicationPlant.getPlant().getCountry().equals(country))
                .findAny();
        if (applicationPlantOpt.isPresent()) {
            return applicationPlantOpt.get();
        }
        log.error("ApplicationPlant relationship couldn't be found. country : {}", country);
        throw new IllegalArgumentException("ApplicationPlant relationship couldn't be found!");
    }
}
