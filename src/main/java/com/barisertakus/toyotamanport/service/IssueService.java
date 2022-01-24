package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.dto.IssueDTO;
import com.barisertakus.toyotamanport.entity.ApplicationServerIssue;
import com.barisertakus.toyotamanport.entity.Issue;

import java.util.List;

public interface IssueService {
    List<Issue> saveAll(List<Issue> issues);
}
