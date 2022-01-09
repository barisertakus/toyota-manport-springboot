package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class BaseEntity extends Auditable<String> {
    private Boolean isActive = true;
}
