package com.rathon.manatee.z;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String industry;
    private String registrationNumber;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<OrgUnit> orgUnits = new ArrayList<>();

    public Company(String name, String address, String industry, String registrationNumber) {
        this.name = name;
        this.address = address;
        this.industry = industry;
        this.registrationNumber = registrationNumber;
    }

    public Company() {
        this.name = "default name";
        this.address = "default address";
        this.industry = "default industry";
        this.registrationNumber = "default number";
    }

    public Company(int i) {
        this.name = String.valueOf(i);
        this.address = String.valueOf(i);
        this.industry = String.valueOf(i);
        this.registrationNumber = String.valueOf(i);
    }

    @Override
    public String toString() {
        return String.format("Company: {\n\tid: %d,\n\tname: %s,\n\taddress: %s,\n\tindustry: %s,\n\tregNum: %s\n}", id, name, address, industry, registrationNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnit> orgUnits) {
        this.orgUnits = orgUnits;
    }
}