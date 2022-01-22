package com.barisertakus.toyotamanport.enums;

public enum ServerType {
    PRODUCTION,
    TEST;

    public ServerType findByValue(String value){
        for(ServerType serverType : ServerType.values()){
            if(value.equals(serverType.toString()))
                return serverType;
        }
        return null;
    }
}
