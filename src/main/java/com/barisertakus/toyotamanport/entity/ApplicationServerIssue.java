package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ApplicationServerIssue extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

}
