package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.ApplicationCreateDTO;

public interface ApplicationService {
    Boolean save(ApplicationCreateDTO applicationCreateDTO);
}
