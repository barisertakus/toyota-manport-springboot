package com.barisertakus.toyotamanport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDashboardDTO {
    private String issueType;
    private String impactType;
}
