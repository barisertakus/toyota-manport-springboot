package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PlantDashboardDTO {
    private String country;
    private List<ServerDashboardDTO> servers = new ArrayList<>();
}
