package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfrastructureCreateDTO {

    private Long id;

    private String jdkVersion;

    private String jettyVersion;

    private String nodeJsVersion;

    private String country;
}
