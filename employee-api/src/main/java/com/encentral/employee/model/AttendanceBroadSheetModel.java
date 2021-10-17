package com.encentral.employee.model;

import com.encentral.entities.JpaEmployee;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class AttendanceBroadSheetModel {
    private int id;
    private JpaEmployee employeeId;
    private String date;
    private String time;

    public AttendanceBroadSheetModel() {
    }

    public AttendanceBroadSheetModel(int id, JpaEmployee employeeId, String date, String time) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JpaEmployee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(JpaEmployee employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
