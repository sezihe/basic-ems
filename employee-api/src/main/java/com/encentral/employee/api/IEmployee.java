package com.encentral.employee.api;

import com.encentral.employee.model.*;

import java.util.List;
import java.util.Optional;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
public interface IEmployee {

    EmployeeLoginResponseModel login(EmployeeLoginRequestModel employeeDetails);

    boolean changePassword(String userToken, EmployeeChangePasswordModel changePasswordModel) throws IllegalAccessException;

    boolean markAttendance(String userToken);

    Optional<EmployeeModel> addEmployee(String userToken, EmployeeModel employeeModel) throws IllegalAccessException;

    Optional<EmployeeModel> removeEmployee(String userToken, String employeeId) throws IllegalAccessException;

    Optional<List<EmployeeModel>> getEmployees(String userToken) throws IllegalAccessException;

    Optional<List<AttendanceBroadSheetModel>> getAttendanceBroadsheet(String userToken) throws IllegalAccessException;
}
