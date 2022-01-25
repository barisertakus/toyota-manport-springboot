package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Infrastructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jdkVersion;

    private String jettyVersion;

    private String nodeJsVersion;

    @OneToMany(mappedBy = "infrastructure")
    List<ApplicationPlant> applicationPlants = new ArrayList<>();
}
