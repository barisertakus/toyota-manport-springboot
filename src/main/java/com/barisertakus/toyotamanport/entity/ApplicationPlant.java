package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"application_id", "plant_id"})
})
public class ApplicationPlant extends BaseEntity{

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private Boolean track;
}
