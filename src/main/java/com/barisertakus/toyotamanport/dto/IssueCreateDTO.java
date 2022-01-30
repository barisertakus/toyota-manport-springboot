package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueCreateDTO {
    private Long id;

    private String issueType;

    private String impactType;

    private String description;

    private Boolean track = true;

    private String country;
}
