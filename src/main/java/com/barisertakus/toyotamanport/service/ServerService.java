package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.entity.Plant;
import com.barisertakus.toyotamanport.entity.Server;

import java.util.List;

public interface ServerService {
    List<Server> getByCountryNames(List<String> countryNames);
    Boolean addServersToPlant(Plant plant);
}
