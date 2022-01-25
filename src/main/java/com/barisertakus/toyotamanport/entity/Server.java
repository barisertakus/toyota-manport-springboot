package com.barisertakus.toyotamanport.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString(of = { "id", "name"})
public class Server extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @OneToMany(mappedBy = "server")
    List<ApplicationServerIssue> applicationServerIssues = new ArrayList<>();
}
