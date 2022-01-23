package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.dto.IssueDTO;
import com.barisertakus.toyotamanport.entity.Issue;
import com.barisertakus.toyotamanport.repository.IssueRepository;
import com.barisertakus.toyotamanport.service.IssueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper) {
        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Issue> saveAll(List<IssueCreateDTO> issueDTOS) {

        List<Issue> issues = issueDTOS.stream()
                .map(issueDTO -> modelMapper.map(issueDTO, Issue.class)).collect(Collectors.toList());
        return issueRepository.saveAll(issues);
    }
}
