package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.ApplicationServerIssue;
import com.barisertakus.toyotamanport.entity.Issue;
import com.barisertakus.toyotamanport.entity.Server;
import com.barisertakus.toyotamanport.repository.ApplicationServerIssueRepository;
import com.barisertakus.toyotamanport.service.ApplicationServerIssueService;
import com.barisertakus.toyotamanport.service.IssueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServerIssueServiceImpl implements ApplicationServerIssueService {
    private final ApplicationServerIssueRepository applicationServerIssueRepository;
    private final IssueService issueService;

    public ApplicationServerIssueServiceImpl(ApplicationServerIssueRepository applicationServerIssueRepository, IssueService issueService) {
        this.applicationServerIssueRepository = applicationServerIssueRepository;
        this.issueService = issueService;
    }

    @Override
    public Boolean saveAll(Application application, Server server, List<IssueCreateDTO> issueDTOList){
        List<Issue> issues = issueService.saveAll(issueDTOList);
        List<ApplicationServerIssue> applicationServerIssues = issues.stream().map(issue -> {
            ApplicationServerIssue applicationServerIssue = new ApplicationServerIssue(application, server, issue);
            addApplicationServerIssueToObjects(applicationServerIssue, application, server, issue);
            return applicationServerIssue;
        }).collect(Collectors.toList());
        applicationServerIssueRepository.saveAll(applicationServerIssues);
        return true;
    }

    private void addApplicationServerIssueToObjects(ApplicationServerIssue applicationServerIssue, Application application, Server server, Issue issue){
        application.getApplicationServerIssues().add(applicationServerIssue);
        server.getApplicationServerIssues().add(applicationServerIssue);
        issue.getApplicationServerIssues().add(applicationServerIssue);
    }
}
