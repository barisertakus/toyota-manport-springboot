package com.barisertakus.toyotamanport.entity;

import javax.persistence.*;
import com.barisertakus.toyotamanport.enums.ImpactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString(of = { "id", "issueType" })
public class Issue extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issueType;

    @Enumerated(EnumType.STRING)
    private ImpactType impactType;

    private String description;

    @ManyToOne
    @JoinColumn(name = "application_plant_id")
    ApplicationPlant applicationPlant;
}
