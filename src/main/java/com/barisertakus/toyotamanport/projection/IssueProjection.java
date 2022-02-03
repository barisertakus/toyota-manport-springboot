package com.barisertakus.toyotamanport.projection;


import com.barisertakus.toyotamanport.enums.ImpactType;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface IssueProjection {
    Long getId();

    Date getCreatedDate();

    @Value("#{target.applicationPlant.application.shortName}")
    String getApplicationName();

    String getIssueType();

    ImpactType getImpactType();

    @Value("#{target.applicationPlant.plant.country}")
    String getPlantCountry();

    String getDescription();
}
