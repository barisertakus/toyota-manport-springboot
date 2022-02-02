package com.barisertakus.toyotamanport.enums;

public enum BusinessAreaType {
    Manufacturing,
    Quality,
    Both,
    Other;

    public BusinessAreaType findByValue(String value){
        for(BusinessAreaType businessAreaType : BusinessAreaType.values()){
            if(value.equals(businessAreaType.toString()))
                return businessAreaType;
        }
        return null;
    }
}

