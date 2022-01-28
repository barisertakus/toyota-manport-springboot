package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.*;
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

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final InfrastructureService infrastructureService;
    private final ApplicationPlantService applicationPlantService;
    private final ApplicationServerIssueService applicationServerIssueService;
    private final IssueService issueService;
    private final LinkService linkService;
    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, InfrastructureService infrastructureService, ApplicationPlantService applicationPlantService, ApplicationServerIssueService applicationServerIssueService, IssueService issueService, LinkService linkService, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.infrastructureService = infrastructureService;
        this.applicationPlantService = applicationPlantService;
        this.applicationServerIssueService = applicationServerIssueService;
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
        applicationServerIssueService.saveAll(application, issues);
        return true;
    }

    @Override
    public Page<ApplicationManagementDTO> getAll(int pageNo, int pageSize, String sortType, String sortField) {
        Pageable pageable = CreatePageable.create(pageNo, pageSize, sortType, sortField);
        Page<ApplicationManagementDTO> applications = applicationRepository.findApplicationsWithLivePlants(pageable);
        return applications;
    }
}
