package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.repository.ApplicationPlantRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ApplicationPlantServiceImpl implements ApplicationPlantService {
    private final ApplicationPlantRepository applicationPlantRepository;

    public ApplicationPlantServiceImpl(ApplicationPlantRepository applicationPlantRepository) {
        this.applicationPlantRepository = applicationPlantRepository;
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
}
