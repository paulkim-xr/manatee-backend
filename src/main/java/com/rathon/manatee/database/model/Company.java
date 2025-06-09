package com.rathon.manatee.database.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    private Long id;
    private String name;
    private String address;
    private int industryId;
    private String registrationNumber;
}
