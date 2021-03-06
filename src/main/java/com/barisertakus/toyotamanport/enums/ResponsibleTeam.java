package com.barisertakus.toyotamanport.enums;

public enum ResponsibleTeam {
    ManufacturingSystems("Manufacturing Systems"),
    EMCQuality("EMC Quality"),
    Other("Other");

    private final String team;

    ResponsibleTeam(String team){
        this.team = team;
    }

    public static ResponsibleTeam findByValue(String value) {
        for (ResponsibleTeam responsibleTeam : ResponsibleTeam.values()) {
            if (value.equals(responsibleTeam.team)) {
                return responsibleTeam;
            }
        }
        return null;
    }
}
