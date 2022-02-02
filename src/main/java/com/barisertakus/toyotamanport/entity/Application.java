package com.barisertakus.toyotamanport.entity;

import com.barisertakus.toyotamanport.enums.BusinessAreaType;
import com.barisertakus.toyotamanport.enums.ResponsibleTeam;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(of = {"id", "shortName"})
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
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

    @Enumerated(EnumType.STRING)
    private BusinessAreaType businessAreaType;

    @Enumerated(EnumType.STRING)
    private ResponsibleTeam responsibleTeam;

    @JsonManagedReference
    @OneToMany(mappedBy = "application")
    List<ApplicationPlant> applicationPlants = new ArrayList<>();

}
