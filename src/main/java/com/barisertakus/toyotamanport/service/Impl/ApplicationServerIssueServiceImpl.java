package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.ApplicationServerIssue;
import com.barisertakus.toyotamanport.entity.Issue;
import com.barisertakus.toyotamanport.entity.Server;
import com.barisertakus.toyotamanport.repository.ApplicationServerIssueRepository;
import com.barisertakus.toyotamanport.service.ApplicationServerIssueService;
import com.barisertakus.toyotamanport.service.IssueService;
import com.barisertakus.toyotamanport.service.ServerService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApplicationServerIssueServiceImpl implements ApplicationServerIssueService {
    private final ApplicationServerIssueRepository applicationServerIssueRepository;
    private final ServerService serverService;
    private final IssueService issueService;
    private final ModelMapper modelMapper;

    public ApplicationServerIssueServiceImpl(ApplicationServerIssueRepository applicationServerIssueRepository, ServerService serverService, IssueService issueService, ModelMapper modelMapper) {
        this.applicationServerIssueRepository = applicationServerIssueRepository;
        this.serverService = serverService;
        this.issueService = issueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean saveAll(Application application, List<IssueCreateDTO> issueDTOList) {
        List<Issue> issues = new ArrayList<>();
        List<Server> servers = getServersByIssueList(issueDTOList);
        List<ApplicationServerIssue> applicationServerIssues = issueDTOList.stream().map(issueDTO -> {
            Server server = findInServerListByServerAndCountry(servers, issueDTO.getCountry(), issueDTO.getServer());
            Issue issue = modelMapper.map(issueDTO, Issue.class);
            issues.add(issue);
            ApplicationServerIssue applicationServerIssue = new ApplicationServerIssue(application, server, issue);
            addApplicationServerIssueToObjects(applicationServerIssue, application, server, issue);
            return applicationServerIssue;
        }).collect(Collectors.toList());
        issueService.saveAll(issues);
        applicationServerIssueRepository.saveAll(applicationServerIssues);
        return true;
    }

    private List<Server> getServersByIssueList(List<IssueCreateDTO> issueDTOList) {
        List<String> countryNames = getCountryNamesFromIssueList(issueDTOList);
        List<Server> servers = getServersByCountryNames(countryNames);
        return servers;
    }

    private List<String> getCountryNamesFromIssueList(List<IssueCreateDTO> issueDTOList) {
        return issueDTOList.stream().map(issueCreateDTO -> issueCreateDTO.getCountry())
                .collect(Collectors.toList());
    }

    private List<Server> getServersByCountryNames(List<String> countryNames) {
        return serverService.getByCountryNames(countryNames);
    }

    private Server findInServerListByServerAndCountry(List<Server> servers, String countryName, String serverName) {
        Optional<Server> serverOptional = servers.stream().filter(server ->
                server.getPlant().getCountry().equals(countryName)
                        && server.getName().equals(serverName)).findFirst();
        if (serverOptional.isPresent()) {
            return serverOptional.get();
        }
        log.error("Server record couldn't be found with parameter serverName : {} , countryName : {} !", serverName, countryName);
        throw new IllegalArgumentException("Server record couldn't be found!");
    }

    private void addApplicationServerIssueToObjects(ApplicationServerIssue applicationServerIssue, Application application, Server server, Issue issue) {
        application.getApplicationServerIssues().add(applicationServerIssue);
        server.getApplicationServerIssues().add(applicationServerIssue);
        issue.getApplicationServerIssues().add(applicationServerIssue);
    }
}
