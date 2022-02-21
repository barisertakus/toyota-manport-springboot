package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.*;
import com.barisertakus.toyotamanport.dto.ApplicationDashboardDTO;
import com.barisertakus.toyotamanport.entity.*;
import com.barisertakus.toyotamanport.repository.ApplicationRepository;
import com.barisertakus.toyotamanport.service.*;
import com.barisertakus.toyotamanport.utils.CreatePageable;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
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
        return saveApplicationDetails(applicationCreateDTO, application);
    }

    public Boolean saveApplicationDetails(ApplicationCreateDTO applicationCreateDTO, Application application){
        List<PlantWithTrackDTO> plants = applicationCreateDTO.getPlants();
        List<InfrastructureCreateDTO> infrastructures = applicationCreateDTO.getInfrastructures();
        List<LinkCreateDTO> links = applicationCreateDTO.getLinks();
        List<IssueCreateDTO> issues = applicationCreateDTO.getIssues();
        List<ApplicationPlant> applicationPlants = applicationPlantService.saveByApplication(application, plants, infrastructures);
        if(!applicationPlants.isEmpty()){
            linkService.saveAll(links, applicationPlants);
            issueService.saveAll(issues, applicationPlants);
            return true;
        }
        log.info("The application has been recorded without being installed in any country.");
        return false;
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

    @Override
    public ApplicationViewDTO getByShortName(String shortName) {
        Application application = applicationRepository.findByShortName(shortName);
        List<IssueCreateDTO> issues = new ArrayList<>();
        List<LinkCreateDTO> links = new ArrayList<>();
        List<PlantWithTrackDTO> plants = new ArrayList<>();
        List<InfrastructureCreateDTO> infrastructures = new ArrayList<>();
        for(ApplicationPlant applicationPlant : application.getApplicationPlants()) {

            Plant plant = applicationPlant.getPlant();
            if(plant != null){
                PlantWithTrackDTO plantWithTrackDTO = modelMapper.map(plant, PlantWithTrackDTO.class);
                plantWithTrackDTO.setTrack(applicationPlant.getTrack());
                plants.add(plantWithTrackDTO);
            }

            List<IssueCreateDTO> issuesAdd = applicationPlant.getIssues().stream().map(issue -> {
                IssueCreateDTO issueCreateDTO = modelMapper.map(issue, IssueCreateDTO.class);
                issueCreateDTO.setCountry(plant.getCountry());
                return issueCreateDTO;
            }).collect(Collectors.toList());
            issues.addAll(issuesAdd);

            List<LinkCreateDTO> linksAdd = applicationPlant.getLinks().stream().map(link -> {
                LinkCreateDTO linkCreateDTO = modelMapper.map(link, LinkCreateDTO.class);
                if(plant != null)
                    linkCreateDTO.setCountry(plant.getCountry());
                return linkCreateDTO;
            }).collect(Collectors.toList());
            links.addAll(linksAdd);

            Infrastructure infrastructure = applicationPlant.getInfrastructure();
            if(infrastructure != null){
                InfrastructureCreateDTO infrastructureDTO =
                        modelMapper.map(applicationPlant.getInfrastructure(), InfrastructureCreateDTO.class);
                if(plant != null)
                    infrastructureDTO.setCountry(plant.getCountry());
                infrastructures.add(infrastructureDTO);
            }
        }
        return generateApplicationView(application, plants, infrastructures, issues, links);
    }

    @Override
    public Application getById(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        return application.orElse(null);
    }

    @Override
    public Boolean edit(ApplicationCreateDTO applicationCreateDTO) {
        Application application = getById(applicationCreateDTO.getId());
        Application applicationMap = modelMapper.map(applicationCreateDTO, Application.class);
        applicationRepository.save(applicationMap);
        editApplicationDetails(applicationCreateDTO, application);
        return true;
    }

    public Boolean editApplicationDetails(ApplicationCreateDTO applicationCreateDTO, Application application){
        List<ApplicationPlant> applicationPlants = applicationPlantService.editApplicationPlants(
                application, applicationCreateDTO.getPlants(), applicationCreateDTO.getInfrastructures()
        );

        linkService.saveAll(applicationCreateDTO.getLinks(), applicationPlants);
        issueService.saveAll(applicationCreateDTO.getIssues(), applicationPlants);
        return true;
    }

    public ApplicationViewDTO generateApplicationView (
            Application application, List<PlantWithTrackDTO> plants, List<InfrastructureCreateDTO> infrastructures,
            List<IssueCreateDTO> issues, List<LinkCreateDTO> links) {
        return new ApplicationViewDTO(
                application.getId(), application.getFullName(), application.getShortName(),
                application.getTrack(), application.getLineStopRisk(), application.getLineCountOfBackendCode(),
                application.getLineCountOfFrontendCode(), application.getLivePlants(),
                application.getReleaseDate(), application.getResponsible(), application.getBackend(),
                application.getFrontend(), application.getDatabase(), application.getBusinessAreaType(),
                application.getResponsibleTeam(),
                plants, infrastructures, issues, links);
    }
}
