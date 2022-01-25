package com.barisertakus.toyotamanport.dto;

import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Infrastructure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPlantsAndInfrastructuresDTO {
    List<ApplicationPlant> applicationPlants = new ArrayList<>();
    List<Infrastructure> infrastructures = new ArrayList<>();
}
