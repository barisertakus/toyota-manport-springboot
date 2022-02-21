package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ApplicationPlantDTO {
    private PlantDashboardDTO plant;
    private List<IssueDTO> issues = new ArrayList<>();
    private List<LinkDTO> links = new ArrayList<>();
}
