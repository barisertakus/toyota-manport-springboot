package com.barisertakus.toyotamanport.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(of = { "id", "name"})
@NoArgsConstructor
public class Server extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public Server(String name, Plant plant) {
        this.name = name;
        this.plant = plant;
    }
}
