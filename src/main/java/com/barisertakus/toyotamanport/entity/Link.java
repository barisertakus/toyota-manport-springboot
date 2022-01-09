package com.barisertakus.toyotamanport.entity;

import com.barisertakus.toyotamanport.enums.ServerType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Link extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appNode1;

    private String appNode2;

    private String loadBalancer;

    private String logs1;

    private String logs2;

    private String healthPage1;

    private String healthPage2;

    private String monitoring1;

    private String monitoring2;

    private String failoverUrl;

    private String userSchema;

    private String dbNode1;

    private String dbNode2;

    private ServerType serverType; // prod-test

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;
}
