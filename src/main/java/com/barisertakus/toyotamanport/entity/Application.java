package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.barisertakus.toyotamanport.enums.*;

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

    private BusinessAreaType businessAreaType;

    @Enumerated(EnumType.STRING)
    private ResponsibleTeam responsibleTeam;

    @ManyToMany
    @JoinTable(
            name = "application_plants",
            joinColumns = @JoinColumn(name="application_id"),
            inverseJoinColumns = @JoinColumn(name="plant_id"))
    private List<Plant> plants;

    @OneToMany(mappedBy = "application")
    List<ApplicationServerIssue> applicationServerIssues;
}
