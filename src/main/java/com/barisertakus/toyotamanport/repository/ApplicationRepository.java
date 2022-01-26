package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
