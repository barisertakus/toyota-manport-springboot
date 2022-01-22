package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id", "plant_id"})
})
public class ApplicationPlant extends BaseEntity{

    @Id
    private Long id;

    private Boolean track;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @OneToMany(mappedBy = "applicationPlant")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "applicationPlant")
    private List<Infrastructure> infrastructures = new ArrayList<>();

}
