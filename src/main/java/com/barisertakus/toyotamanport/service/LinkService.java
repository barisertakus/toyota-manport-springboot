package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.LinkCreateDTO;
import com.barisertakus.toyotamanport.dto.LinkDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;

import java.util.List;

public interface LinkService {
    Boolean saveAll(List<LinkCreateDTO> linkDTOList, List<ApplicationPlant> applicationPlants);
}
