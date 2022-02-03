package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.IssueCreateDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.projection.IssueProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IssueService {
    Boolean saveAll(List<IssueCreateDTO> issueDTOList, List<ApplicationPlant> applicationPlants);
    Page<IssueProjection> findAllByIsActiveTrue(int pageNo, int pageSize, String sortType, String sortField);
}
