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

    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private Infrastructure infrastructure;

    @OneToMany(mappedBy = "applicationPlant")
    private List<Link> links = new ArrayList<>();

    public ApplicationPlant(Boolean track, Application application, Plant plant) {
        this.track = track;
        this.application = application;
        this.plant = plant;
    }
}
