package com.encentral.employee.model;

import com.encentral.entities.JpaEmployee;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class EmployeeMapper {

    public static EmployeeModel jpaEmployeeToEmployee(JpaEmployee jpaEmployee) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setEmployeeId(jpaEmployee.getEmployeeId());
        employeeModel.setEmployeeEmail(jpaEmployee.getEmployeeEmail());
        employeeModel.setEmployeeName(jpaEmployee.getEmployeeName());
        employeeModel.setEmployeePassword(jpaEmployee.getEmployeePassword());
        employeeModel.setCreatedAt(jpaEmployee.getCreatedAt());

        return employeeModel;
    }

    public static JpaEmployee employeeToJpaEmployee(EmployeeModel employeeModel) {
        JpaEmployee jpaEmployee = new JpaEmployee();
        jpaEmployee.setEmployeeId(employeeModel.getEmployeeId());
        jpaEmployee.setEmployeeEmail(employeeModel.getEmployeeEmail());
        jpaEmployee.setEmployeeName(employeeModel.getEmployeeName());
        jpaEmployee.setEmployeePassword(employeeModel.getEmployeePassword());
        jpaEmployee.setCreatedAt(employeeModel.getCreatedAt());

        return jpaEmployee;
    }
}
