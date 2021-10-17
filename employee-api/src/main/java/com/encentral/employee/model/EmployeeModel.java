package com.encentral.employee.model;

import java.util.Date;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
public class EmployeeModel {
    private String employeeId;
    private String employeeEmail;
    private String employeeName;
    private String employeePassword;
    private Date createdAt;

    public EmployeeModel() {
    }

    public EmployeeModel(String employeeEmail, String employeeName, String employeePassword, Date createdAt) {
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.employeePassword = employeePassword;
        this.createdAt = createdAt;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
        this.employeeEmail = employeePassword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
