package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
    private String shortName;

    private Boolean track = true;

    private Boolean lineStopRisk = false;

    private int lineCountOfBackendCode;

    private int lineCountOfFrontendCode;
    
    private int livePlants;

    private LocalDate releaseDate;

    private String responsible;

    private String backend;

    private String frontend;

    private String database;

    @Enumerated(EnumType.STRING)
    private BusinessAreaType businessAreaType;

    @Enumerated(EnumType.STRING)
    private ResponsibleTeam responsibleTeam;
}
