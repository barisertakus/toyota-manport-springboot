package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ServerDashboardDTO {
    private String name;
    private List<IssueDashboardDTO> issues = new ArrayList<>();
}
