package com.barisertakus.toyotamanport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueSearchDTO {

    String applicationName;

    Boolean status;

    Timestamp fromDate;


    Timestamp toDate;
}
