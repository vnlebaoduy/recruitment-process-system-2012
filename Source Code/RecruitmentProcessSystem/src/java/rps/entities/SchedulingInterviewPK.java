/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author user
 */
@Embeddable
public class SchedulingInterviewPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "EmployeeID")
    private String employeeID;
    @Basic(optional = false)
    @Column(name = "ApplicantID")
    private String applicantID;

    public SchedulingInterviewPK() {
    }

    public SchedulingInterviewPK(String employeeID, String applicantID) {
        this.employeeID = employeeID;
        this.applicantID = applicantID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        hash += (applicantID != null ? applicantID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchedulingInterviewPK)) {
            return false;
        }
        SchedulingInterviewPK other = (SchedulingInterviewPK) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        if ((this.applicantID == null && other.applicantID != null) || (this.applicantID != null && !this.applicantID.equals(other.applicantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rps.entities.SchedulingInterviewPK[employeeID=" + employeeID + ", applicantID=" + applicantID + "]";
    }

}
