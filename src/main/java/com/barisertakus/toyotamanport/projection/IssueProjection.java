package com.barisertakus.toyotamanport.projection;


import com.barisertakus.toyotamanport.enums.ImpactType;

import java.util.Date;

public interface IssueProjection {
    Long getId();

    Date getUpdatedDate();

    String getApplicationName();

    String getIssueType();

    ImpactType getImpactType();

    String getPlantCountry();

    String getDescription();
}
