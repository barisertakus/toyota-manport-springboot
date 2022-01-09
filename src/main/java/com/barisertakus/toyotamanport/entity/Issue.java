package com.barisertakus.toyotamanport.entity;

import javax.persistence.*;
import com.barisertakus.toyotamanport.enums.ImpactType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issueType;

    @Enumerated(EnumType.STRING)
    private ImpactType impactType;

    private String description;

    @OneToMany(mappedBy = "issue")
    List<ApplicationServerIssue> applicationServerIssues = new ArrayList<>();

}
