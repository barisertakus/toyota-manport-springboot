package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server, Long> {
    List<Server> findByPlant_CountryIn(List<String> countryNames);
}
