package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Role extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;
}
