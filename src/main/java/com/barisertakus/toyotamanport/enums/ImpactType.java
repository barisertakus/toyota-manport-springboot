package com.barisertakus.toyotamanport.enums;

public enum ImpactType {
    High,
    Medium,
    Low;

    public ImpactType findByValue(String value){
        for(ImpactType impactType : ImpactType.values()){
            if(value.equals(impactType.toString()))
                return impactType;
        }
        return null;
    }
}

