package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantWithTrackDTO {
    private Long id;

    private String country;

    private String fullName;

    private String shortCode;

    private int liveAppCount;

    private Boolean track;
}
