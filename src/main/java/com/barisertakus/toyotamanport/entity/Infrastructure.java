package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Infrastructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jdkVersion;

    private String jettyVersion;

    private String nodeJsVersion;

    @ManyToOne
    @JoinColumn(name = "application_plant_id")
    ApplicationPlant applicationPlant;
}
