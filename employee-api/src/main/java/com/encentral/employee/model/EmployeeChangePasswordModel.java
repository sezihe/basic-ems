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

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
