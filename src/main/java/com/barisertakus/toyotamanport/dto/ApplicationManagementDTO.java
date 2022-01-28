package com.barisertakus.toyotamanport.dto;

import com.barisertakus.toyotamanport.enums.BusinessAreaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationManagementDTO {
    private Long id;

    private String shortName;

    private Boolean track;

    private Boolean lineStopRisk;

    private long livePlants;

    private BusinessAreaType businessAreaType;
}
