package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationDashboardDTO {
    private Long id;
    private String shortName;
    private List<ApplicationPlantDashboardDTO> applicationPlants = new ArrayList<>();
}
