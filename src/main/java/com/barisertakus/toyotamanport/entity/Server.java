package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @OneToMany(mappedBy = "server")
    List<ApplicationServerIssue> applicationServerIssues;
}
