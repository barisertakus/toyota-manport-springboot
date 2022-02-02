package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.*;
import com.barisertakus.toyotamanport.dto.ApplicationDashboardDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.repository.ApplicationRepository;
import com.barisertakus.toyotamanport.service.*;
import com.barisertakus.toyotamanport.utils.CreatePageable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationPlantService applicationPlantService;
    private final IssueService issueService;
    private final LinkService linkService;
    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationPlantService applicationPlantService, IssueService issueService, LinkService linkService, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationPlantService = applicationPlantService;
        this.issueService = issueService;
        this.linkService = linkService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Boolean save(ApplicationCreateDTO applicationCreateDTO) {
        Application application = modelMapper.map(applicationCreateDTO, Application.class);
        applicationRepository.save(application);
        List<PlantWithTrackDTO> plants = applicationCreateDTO.getPlants();
        List<InfrastructureCreateDTO> infrastructures = applicationCreateDTO.getInfrastructures();
        List<LinkCreateDTO> links = applicationCreateDTO.getLinks();
        List<IssueCreateDTO> issues = applicationCreateDTO.getIssues();
        List<ApplicationPlant> applicationPlants = applicationPlantService.saveByApplication(application, plants, infrastructures);
        linkService.saveAll(links, applicationPlants);
        issueService.saveAll(issues, applicationPlants);
        return true;
    }

    @Override
    public Page<ApplicationManagementDTO> getAll(int pageNo, int pageSize, String sortType, String sortField) {
        Pageable pageable = CreatePageable.create(pageNo, pageSize, sortType, sortField);
        Page<ApplicationManagementDTO> applications = applicationRepository.findApplicationsWithLivePlants(pageable);
        return applications;
    }

    @Override
    public List<ApplicationDashboardDTO> getAllForDashboard() {
        List<Application> applications = applicationRepository.findByIsActiveTrue();
        List<ApplicationDashboardDTO> applicationDashboardDTOS = applications.stream().map(application ->
                modelMapper.map(application, ApplicationDashboardDTO.class)).collect(Collectors.toList());

        return applicationDashboardDTOS;
    }
}
