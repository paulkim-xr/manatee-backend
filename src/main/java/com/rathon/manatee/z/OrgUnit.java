package com.rathon.manatee.z;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OrgUnit {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String type; // e.g., "Division", "Department", "Team"
    private String code;

    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public OrgUnit getParent() {
        return parent;
    }

    public void setParent(OrgUnit parent) {
        this.parent = parent;
    }

    public List<OrgUnit> getChildren() {
        return children;
    }

    public void setChildren(List<OrgUnit> children) {
        this.children = children;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private OrgUnit parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<OrgUnit> children = new ArrayList<>();

    @OneToMany(mappedBy = "orgUnit", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public OrgUnit() {
        this.name = "test name";
        this.type = "test type";
        this.code = "test code";
        this.company = null;
        this.parent = null;
        this.children = null;
        this.employees = null;
    }

    public OrgUnit(String name, String type, String code, Company company, OrgUnit parent, List<OrgUnit> children, List<Employee> employees) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.company = company;
        this.parent = parent;
        this.children = children;
        this.employees = employees;
    }
}