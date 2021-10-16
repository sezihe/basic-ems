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
    private Date date;

    @Column(name = "time_milliseconds")
    @Basic(optional = false)
    private long timeMilliSeconds;

    public JpaAttendanceBroadSheet() {
    }

    public JpaAttendanceBroadSheet(int id, JpaEmployee employee, Date date, long timeMilliSeconds) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.timeMilliSeconds = timeMilliSeconds;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTimeMilliSeconds() {
        return timeMilliSeconds;
    }

    public void setTimeMilliSeconds(long timeMilliSeconds) {
        this.timeMilliSeconds = timeMilliSeconds;
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
