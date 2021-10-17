package com.encentral.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author EZIHE S. DANIEL
 * CreatedAt: 16/10/2021
 */
@Entity
@Table(name = "attendance_broadsheet")
@NamedQueries({
        @NamedQuery(name = "JpaAttendanceBroadSheet.findAll", query = "SELECT a from JpaAttendanceBroadSheet a")
})
public class JpaAttendanceBroadSheet implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private JpaEmployee employee;

    @Column(name = "date", unique = true)
    @Basic(optional = false)
    private String date;

    @Column(name = "time")
    @Basic(optional = false)
    private String time;

    public JpaAttendanceBroadSheet() {
    }

    public JpaAttendanceBroadSheet(int id, JpaEmployee employee, String date, String time) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JpaEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(JpaEmployee employee) {
        this.employee = employee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaAttendanceBroadSheet that = (JpaAttendanceBroadSheet) o;
        return id != 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
