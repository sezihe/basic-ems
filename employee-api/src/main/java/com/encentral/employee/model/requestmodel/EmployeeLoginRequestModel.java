package com.encentral.employee.model.requestmodel;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
public class EmployeeLoginRequestModel {
    private String email;
    private String password;

    public EmployeeLoginRequestModel() {
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
