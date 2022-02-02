package com.barisertakus.toyotamanport.dto;

import com.barisertakus.toyotamanport.enums.BusinessAreaType;
import com.barisertakus.toyotamanport.enums.ResponsibleTeam;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class ApplicationDTO {

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
}
