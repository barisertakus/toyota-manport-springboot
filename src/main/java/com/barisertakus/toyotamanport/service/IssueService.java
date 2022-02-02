package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;

import java.util.List;

public interface IssueService {
    Boolean saveAll(List<IssueCreateDTO> issueDTOList, List<ApplicationPlant> applicationPlants);
}
