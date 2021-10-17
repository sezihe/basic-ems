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
@Table(name = "employee")
@NamedQueries({
        @NamedQuery(name = "JpaEmployee.findAll", query = "select e from JpaEmployee e"),
        @NamedQuery(name = "JpaEmployee.findByEmail", query = "select e from JpaEmployee e where e.employeeEmail = :email")
})
public class JpaEmployee implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String employeeId;

    @Column(name = "email", unique = true)
    @Basic(optional = false)
    private String employeeEmail;

    @Column(name = "name")
    @Basic(optional = false)
    private String employeeName;

    @Column(name = "password")
    @Basic(optional = false)
    private String employeePassword;

    @Column(name = "createdAt")
    @Basic(optional = false)
    private Date createdAt;

    public JpaEmployee() {
    }

    public JpaEmployee(String employeeId, String employeeEmail, String employeeName, String employeePassword, Date createdAt) {
        this.employeeId = employeeId;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaEmployee that = (JpaEmployee) o;
        return employeeId != null && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
