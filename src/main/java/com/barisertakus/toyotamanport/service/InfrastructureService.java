package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.entity.Infrastructure;

import java.util.List;

public interface InfrastructureService {
    Boolean save(Infrastructure infrastructure);
    Boolean saveAll(List<Infrastructure> infrastructures);
}
