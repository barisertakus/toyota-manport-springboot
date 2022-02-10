package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationPlantDashboardDTO {
    private PlantDashboardDTO plant;
    private List<IssueDashboardDTO> issues = new ArrayList<>();
    private List<LinkDTO> links = new ArrayList<>();
}
