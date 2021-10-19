package com.encentral.employee.model.requestmodel;

import com.encentral.employee.model.EmployeeModel;

import java.util.Date;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class AddEmployeeRequestModel {
    private String adminToken;
    private String employeeEmail;
    private String employeeName;
    private String employeePassword;

    public AddEmployeeRequestModel() {
    }

    public AddEmployeeRequestModel(String adminToken, String employeeEmail, String employeeName, String employeePassword) {
        this.adminToken = adminToken;
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.employeePassword = employeePassword;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
}
