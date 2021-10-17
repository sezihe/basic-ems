package com.encentral.employee.model;

import com.encentral.entities.JpaAttendanceBroadSheet;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 17/10/2021
 */
public class AttendanceBroadSheetMapper {
    public static AttendanceBroadSheetModel jpaAttendanceBroadSheetToAttendanceBroadSheet(JpaAttendanceBroadSheet jpaAttendanceBroadSheet) {
        AttendanceBroadSheetModel attendanceBroadSheetModel = new AttendanceBroadSheetModel();
        attendanceBroadSheetModel.setId(jpaAttendanceBroadSheet.getId());
        attendanceBroadSheetModel.setEmployeeId(jpaAttendanceBroadSheet.getEmployee());
        attendanceBroadSheetModel.setDate(jpaAttendanceBroadSheet.getDate());
        attendanceBroadSheetModel.setTime(jpaAttendanceBroadSheet.getTime());

        return attendanceBroadSheetModel;
    }

    public static JpaAttendanceBroadSheet attendanceBroadSheetToJpaAttendanceBroadSheet(AttendanceBroadSheetModel attendanceBroadSheetModel) {
        JpaAttendanceBroadSheet jpaAttendanceBroadSheet = new JpaAttendanceBroadSheet();
        jpaAttendanceBroadSheet.setId(attendanceBroadSheetModel.getId());
        jpaAttendanceBroadSheet.setEmployee(attendanceBroadSheetModel.getEmployeeId());
        jpaAttendanceBroadSheet.setDate(attendanceBroadSheetModel.getDate());
        jpaAttendanceBroadSheet.setTime(attendanceBroadSheetModel.getTime());

        return jpaAttendanceBroadSheet;
    }
}
