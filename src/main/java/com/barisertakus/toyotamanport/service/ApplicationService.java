package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.ApplicationCreateDTO;
import com.barisertakus.toyotamanport.dto.ApplicationManagementDTO;
import com.barisertakus.toyotamanport.dto.ApplicationDashboardDTO;
import com.barisertakus.toyotamanport.dto.ApplicationViewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    Boolean save(ApplicationCreateDTO applicationCreateDTO);

    Page<ApplicationManagementDTO> getAll(int pageNo, int pageSize, String sortType, String sortField);

    List<ApplicationDashboardDTO> getAllForDashboard();

    ApplicationViewDTO getByShortName(String shortName);
}
