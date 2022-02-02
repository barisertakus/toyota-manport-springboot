package com.barisertakus.toyotamanport.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkCreateDTO {
    private Long id;

    private String appNode1;

    private String appNode2;

    private String loadBalancer;

    private String logs1;

    private String logs2;

    private String healthPage1;

    private String healthPage2;

    private String monitoring1;

    private String monitoring2;

    private String failoverUrl;

    private String userSchema;

    private String dbNode1;

    private String dbNode2;

    private String serverType;

    private String country;
}
