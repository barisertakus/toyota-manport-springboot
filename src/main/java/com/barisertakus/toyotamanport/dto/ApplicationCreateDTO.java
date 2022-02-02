package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApplicationCreateDTO {

    private Long id;

    private String fullName;

    private String shortName;

    private Boolean track = true;

    private Boolean lineStopRisk;

    private int lineCountOfBackendCode;

    private int lineCountOfFrontendCode;

    private int livePlants;

    private LocalDate releaseDate;

    private String responsible;

    private String backend;

    private String frontend;

    private String database;

    private String businessAreaType;

    private String responsibleTeam;

    List<PlantWithTrackDTO> plants = new ArrayList<>();

    List<IssueCreateDTO> issues = new ArrayList<>();

    List<InfrastructureCreateDTO> infrastructures = new ArrayList<>();

    List<LinkCreateDTO> links = new ArrayList<>();
}
