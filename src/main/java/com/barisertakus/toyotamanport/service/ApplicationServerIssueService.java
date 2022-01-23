package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.Server;

import java.util.List;

public interface ApplicationServerIssueService {

    Boolean saveAll(Application application, Server server, List<IssueCreateDTO> issueDTOList);

}
