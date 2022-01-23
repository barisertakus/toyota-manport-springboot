package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueDTO {

    private Long id;

    private String issueType;

    private String impactType;

    private String description;
}
