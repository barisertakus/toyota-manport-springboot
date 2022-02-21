package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.entity.Plant;
import com.barisertakus.toyotamanport.entity.Server;
import com.barisertakus.toyotamanport.repository.ServerRepository;
import com.barisertakus.toyotamanport.service.ServerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Boolean addServersToPlant(Plant plant){
        List<Server> servers = Arrays.asList(
                new Server("PROD-1", plant),
                new Server("PROD-2", plant)
        );
        serverRepository.saveAll(servers);
        return true;
    }

    @Override
    public List<Server> getByCountryNames(List<String> countryNames) {
        return serverRepository.findByPlant_CountryIn(countryNames);
    }
}
