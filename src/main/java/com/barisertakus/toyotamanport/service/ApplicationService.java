package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.ApplicationCreateDTO;
import com.barisertakus.toyotamanport.dto.ApplicationDTO;
import com.barisertakus.toyotamanport.dto.ApplicationManagementDTO;
import com.barisertakus.toyotamanport.entity.Application;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    Boolean save(ApplicationCreateDTO applicationCreateDTO);

    Page<ApplicationManagementDTO> getAll(int pageNo, int pageSize, String sortType, String sortField);

    List<Application> getAllForDashboard();
}
