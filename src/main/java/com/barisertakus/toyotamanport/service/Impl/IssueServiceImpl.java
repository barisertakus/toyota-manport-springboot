package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.*;
import com.barisertakus.toyotamanport.repository.IssueRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.IssueService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ApplicationPlantService applicationPlantService;
    private final ModelMapper modelMapper;

    public IssueServiceImpl(IssueRepository issueRepository, ApplicationPlantService applicationPlantService, ModelMapper modelMapper) {
        this.issueRepository = issueRepository;
        this.applicationPlantService = applicationPlantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean saveAll(List<IssueCreateDTO> issueDTOList, List<ApplicationPlant> applicationPlants) {
        if(applicationPlants != null){
            List<Issue> issues = issueDTOList.stream().map(issueDTO -> {
                ApplicationPlant applicationPlant = applicationPlantService.getApplicationPlantByCountry(issueDTO.getCountry(), applicationPlants);
                Issue issue = modelMapper.map(issueDTO, Issue.class);
                issue.setApplicationPlant(applicationPlant);
                applicationPlant.getIssues().add(issue);
                return issue;
            }).collect(Collectors.toList());
            issueRepository.saveAll(issues);
            return true;
        }
        log.error("Application Plant record couldn't be found.");
        throw new IllegalArgumentException("An error occurred while saving links!");
    }

}
