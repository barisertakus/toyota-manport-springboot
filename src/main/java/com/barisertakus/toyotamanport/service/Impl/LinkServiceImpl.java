package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.LinkDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Link;
import com.barisertakus.toyotamanport.repository.LinkRepository;
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
    private final ModelMapper modelMapper;

    public LinkServiceImpl(LinkRepository linkRepository, ModelMapper modelMapper) {
        this.linkRepository = linkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean saveAll(List<LinkDTO> linkDTOList, ApplicationPlant applicationPlant) {
        if(applicationPlant != null){
            List<Link> links = linkDTOList.stream().map(linkDTO -> {
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
