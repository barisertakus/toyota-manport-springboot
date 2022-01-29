package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.LinkCreateDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Link;
import com.barisertakus.toyotamanport.repository.LinkRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.LinkService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final ApplicationPlantService applicationPlantService;
    private final ModelMapper modelMapper;

    public LinkServiceImpl(LinkRepository linkRepository, ApplicationPlantService applicationPlantService, ModelMapper modelMapper) {
        this.linkRepository = linkRepository;
        this.applicationPlantService = applicationPlantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean saveAll(List<LinkCreateDTO> linkDTOList, List<ApplicationPlant> applicationPlants) {
        if(applicationPlants != null){
            List<Link> links = linkDTOList.stream().map(linkDTO -> {
                ApplicationPlant applicationPlant = applicationPlantService.getApplicationPlantByCountry(linkDTO.getCountry(), applicationPlants);
                Link link = modelMapper.map(linkDTO, Link.class);
                link.setApplicationPlant(applicationPlant);
                applicationPlant.getLinks().add(link);
                return link;
            }).collect(Collectors.toList());
            linkRepository.saveAll(links);
            return true;
        }
        log.error("Application Plant record couldn't be found.");
        throw new IllegalArgumentException("An error occurred while saving links!");
    }
}
