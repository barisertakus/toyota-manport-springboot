package com.barisertakus.toyotamanport.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id", "plant_id"})
})
@NoArgsConstructor
@ToString(of = {"id", "track"})
public class ApplicationPlant extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean track;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "infrastructure_id")
    private Infrastructure infrastructure;

    @JsonManagedReference
    @OneToMany(mappedBy = "applicationPlant", cascade = CascadeType.REMOVE)
    private List<Link> links = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "applicationPlant", cascade = CascadeType.REMOVE)
    private List<Issue> issues = new ArrayList<>();

    public ApplicationPlant(Boolean track, Application application, Plant plant, Infrastructure infrastructure) {
        this.track = track;
        this.application = application;
        this.plant = plant;
        this.infrastructure = infrastructure;
    }
}
