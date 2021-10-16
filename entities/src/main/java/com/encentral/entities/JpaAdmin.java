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
@Table(name = "admin")
@NamedQueries({
        @NamedQuery(name = "JpaAdmin.findAll", query = "SELECT a from JpaAdmin a")
})
public class JpaAdmin implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private JpaEmployee employeeId;

    @Column(name = "createdAt")
    @Basic(optional = false)
    private Date createdAt;

    public JpaAdmin() {
    }

    public JpaAdmin(JpaEmployee employeeId, Date createdAt) {
        this.employeeId = employeeId;
        this.createdAt = createdAt;
    }

    public JpaEmployee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(JpaEmployee employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaAdmin jpaAdmin = (JpaAdmin) o;
        return employeeId != null && Objects.equals(employeeId, jpaAdmin.employeeId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
