package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.InfrastructureDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Infrastructure;
import com.barisertakus.toyotamanport.repository.InfrastructureRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.InfrastructureService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class InfrastructureServiceImpl implements InfrastructureService {
    private final InfrastructureRepository infrastructureRepository;
    private final ApplicationPlantService applicationPlantService;
    private final ModelMapper modelMapper;

    public InfrastructureServiceImpl(InfrastructureRepository infrastructureRepository, ApplicationPlantService applicationPlantService, ModelMapper modelMapper) {
        this.infrastructureRepository = infrastructureRepository;
        this.applicationPlantService = applicationPlantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean save(InfrastructureDTO infrastructureDTO, ApplicationPlant applicationPlant) {
        if (applicationPlant != null) {
            Infrastructure infrastructure = modelMapper.map(infrastructureDTO, Infrastructure.class);
            infrastructure.setApplicationPlant(applicationPlant);
            applicationPlant.getInfrastructures().add(infrastructure);
            infrastructureRepository.save(infrastructure);
            return true;
        }
        log.error("Application Plant record couldn't be found.");
        throw new IllegalArgumentException("An error occurred while saving the Infrastructure!");
    }

    @Override
    public Boolean saveAll(List<InfrastructureDTO> infrastructureDTOList, ApplicationPlant applicationPlant) {
        if (applicationPlant != null) {
            List<Infrastructure> infrastructures = infrastructureDTOList.stream().map(infrastructureDTO -> {
                Infrastructure infrastructure = modelMapper.map(infrastructureDTO, Infrastructure.class);
                infrastructure.setApplicationPlant(applicationPlant);
                applicationPlant.getInfrastructures().add(infrastructure);
                return infrastructure;
            }).collect(Collectors.toList());

            infrastructureRepository.saveAll(infrastructures);
            return true;
        }
        log.error("Application Plant record couldn't be found.");
        throw new IllegalArgumentException("An error occurred while saving infrastructures!");
    }

}
