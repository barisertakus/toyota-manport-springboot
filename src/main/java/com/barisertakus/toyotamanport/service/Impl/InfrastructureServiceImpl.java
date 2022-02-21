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
    private final ModelMapper modelMapper;

    public InfrastructureServiceImpl(InfrastructureRepository infrastructureRepository, ModelMapper modelMapper) {
        this.infrastructureRepository = infrastructureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean save(Infrastructure infrastructure) {
        if (infrastructure != null) {
            infrastructureRepository.save(infrastructure);
            return true;
        }
        log.error("An error occurred while saving the Infrastructure!");
        throw new IllegalArgumentException("An error occurred while saving the Infrastructure!");
    }

    @Override
    public Boolean saveAll(List<Infrastructure> infrastructures) {
        if (infrastructures != null) {
            infrastructureRepository.saveAll(infrastructures);
            return true;
        }
        log.error("An error occurred while saving infrastructures!");
        throw new IllegalArgumentException("An error occurred while saving infrastructures!");
    }

    @Override
    public void deleteAllById(List<Long> idList) {
        if(!idList.isEmpty()){
            infrastructureRepository.deleteAllById(idList);
        }
    }

}
