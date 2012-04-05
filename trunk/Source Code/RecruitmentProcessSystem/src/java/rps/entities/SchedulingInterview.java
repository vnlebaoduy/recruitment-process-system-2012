/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "SchedulingInterview")
@NamedQueries({
    @NamedQuery(name = "SchedulingInterview.findAll", query = "SELECT s FROM SchedulingInterview s"),
    @NamedQuery(name = "SchedulingInterview.findByEmployeeID", query = "SELECT s FROM SchedulingInterview s WHERE s.schedulingInterviewPK.employeeID = :employeeID"),
    @NamedQuery(name = "SchedulingInterview.findByApplicantID", query = "SELECT s FROM SchedulingInterview s WHERE s.schedulingInterviewPK.applicantID = :applicantID"),
    @NamedQuery(name = "SchedulingInterview.findByStatus", query = "SELECT s FROM SchedulingInterview s WHERE s.status = :status"),
    @NamedQuery(name = "SchedulingInterview.findByStartedTime", query = "SELECT s FROM SchedulingInterview s WHERE s.startedTime = :startedTime"),
    @NamedQuery(name = "SchedulingInterview.findByEndedTime", query = "SELECT s FROM SchedulingInterview s WHERE s.endedTime = :endedTime")})
public class SchedulingInterview implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SchedulingInterviewPK schedulingInterviewPK;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "StartedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedTime;
    @Column(name = "EndedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedTime;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "ApplicantID", referencedColumnName = "ApplicantID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Applicant applicant;

    public SchedulingInterview() {
    }

    public SchedulingInterview(SchedulingInterviewPK schedulingInterviewPK) {
        this.schedulingInterviewPK = schedulingInterviewPK;
    }

    public SchedulingInterview(String employeeID, String applicantID) {
        this.schedulingInterviewPK = new SchedulingInterviewPK(employeeID, applicantID);
    }

    public SchedulingInterviewPK getSchedulingInterviewPK() {
        return schedulingInterviewPK;
    }

    public void setSchedulingInterviewPK(SchedulingInterviewPK schedulingInterviewPK) {
        this.schedulingInterviewPK = schedulingInterviewPK;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(Date endedTime) {
        this.endedTime = endedTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schedulingInterviewPK != null ? schedulingInterviewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchedulingInterview)) {
            return false;
        }
        SchedulingInterview other = (SchedulingInterview) object;
        if ((this.schedulingInterviewPK == null && other.schedulingInterviewPK != null) || (this.schedulingInterviewPK != null && !this.schedulingInterviewPK.equals(other.schedulingInterviewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rps.entities.SchedulingInterview[schedulingInterviewPK=" + schedulingInterviewPK + "]";
    }

}
