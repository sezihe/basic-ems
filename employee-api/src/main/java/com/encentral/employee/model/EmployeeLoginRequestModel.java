package com.encentral.employee.model;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
public class EmployeeLoginRequestModel {
    private String email;
    private String password;

    public EmployeeLoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
