package com.encentral.employee.model;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class EmployeeLoginResponseModel {
    private boolean isLoginSuccessful;
    private String token;
    private String errorMessage;

    public EmployeeLoginResponseModel() {
    }

    public EmployeeLoginResponseModel(boolean isLoginSuccessful, String token) {
        this.isLoginSuccessful = isLoginSuccessful;
        this.token = token;
    }

    public boolean isLoginSuccessful() {
        return isLoginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        isLoginSuccessful = loginSuccessful;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
