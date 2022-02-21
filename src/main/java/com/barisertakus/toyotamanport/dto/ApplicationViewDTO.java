package com.barisertakus.toyotamanport.dto;

import com.barisertakus.toyotamanport.entity.Infrastructure;
import com.barisertakus.toyotamanport.enums.BusinessAreaType;
import com.barisertakus.toyotamanport.enums.ResponsibleTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationViewDTO {
    private Long id;

    private String fullName;

    private String shortName;

    private Boolean track;

    private Boolean lineStopRisk;

    private int lineCountOfBackendCode;

    private int lineCountOfFrontendCode;

    private int livePlants;

    private LocalDate releaseDate;

    private String responsible;

    private String backend;

    private String frontend;

    private String database;

    private BusinessAreaType businessAreaType;

    private ResponsibleTeam responsibleTeam;

    private List<PlantWithTrackDTO> plants = new ArrayList<>();

    private List<InfrastructureCreateDTO> infrastructures = new ArrayList<>();

    private List<IssueCreateDTO> issues = new ArrayList<>();

    private List<LinkCreateDTO> links = new ArrayList<>();
}
