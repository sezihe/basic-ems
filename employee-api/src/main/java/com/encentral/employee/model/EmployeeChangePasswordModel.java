package com.encentral.employee.model;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class EmployeeChangePasswordModel {
    private String oldPassword;
    private String newPassword;

    public EmployeeChangePasswordModel() {
    }

    public EmployeeChangePasswordModel(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
