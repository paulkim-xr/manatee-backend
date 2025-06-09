package com.rathon.manatee.z;

import jakarta.persistence.*;

@Entity
public class UserAccount {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String passwordHash;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public UserAccount(String username, String passwordHash, boolean active, Role role, Employee employee) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.active = active;
        this.role = role;
        this.employee = employee;
    }

    public UserAccount() {
        this.username = "default username";
        this.passwordHash = "default password hash";
        this.active = false;
        this.role = Role.STAFF;
        this.employee = null;
    }
}
