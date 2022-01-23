package com.barisertakus.toyotamanport.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

    public ApplicationServerIssue(Application application, Server server, Issue issue) {
        this.application = application;
        this.server = server;
        this.issue = issue;
    }
}
