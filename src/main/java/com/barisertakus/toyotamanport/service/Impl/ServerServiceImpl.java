package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.entity.Server;
import com.barisertakus.toyotamanport.repository.ServerRepository;
import com.barisertakus.toyotamanport.service.ServerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public List<Server> getByCountryNames(List<String> countryNames) {
        return serverRepository.findByPlant_CountryIn(countryNames);
    }
}
