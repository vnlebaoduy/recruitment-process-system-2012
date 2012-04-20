/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Interview")
@NamedQueries({
    @NamedQuery(name = "Interview.findAll", query = "SELECT i FROM Interview i"),
    @NamedQuery(name = "Interview.findByInterviewID", query = "SELECT i FROM Interview i WHERE i.interviewID = :interviewID"),
    @NamedQuery(name = "Interview.findByStatus", query = "SELECT i FROM Interview i WHERE i.status = :status"),
    @NamedQuery(name = "Interview.findByAVStatus", query = "SELECT i FROM Interview i WHERE i.aVStatus = :aVStatus"),
    @NamedQuery(name = "Interview.findByStartedTime", query = "SELECT i FROM Interview i WHERE i.startedTime = :startedTime"),
    @NamedQuery(name = "Interview.findByEndedTime", query = "SELECT i FROM Interview i WHERE i.endedTime = :endedTime")})
public class Interview implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "InterviewID")
    private String interviewID;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "AVStatus")
    private Integer aVStatus;
    @Column(name = "StartedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedTime;
    @Column(name = "EndedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedTime;
    @JoinColumn(name = "VacancyID", referencedColumnName = "VacancyID")
    @ManyToOne(optional = false)
    private Vacancy vacancy;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @ManyToOne
    private Employee employee;
    @JoinColumn(name = "ApplicantID", referencedColumnName = "ApplicantID")
    @ManyToOne(optional = false)
    private Applicant applicant;

    public Interview() {
    }

    public Interview(String interviewID) {
        this.interviewID = interviewID;
    }

    public String getInterviewID() {
        return interviewID;
    }

    public void setInterviewID(String interviewID) {
        this.interviewID = interviewID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAVStatus() {
        return aVStatus;
    }

    public void setAVStatus(Integer aVStatus) {
        this.aVStatus = aVStatus;
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

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
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
        hash += (interviewID != null ? interviewID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interview)) {
            return false;
        }
        Interview other = (Interview) object;
        if ((this.interviewID == null && other.interviewID != null) || (this.interviewID != null && !this.interviewID.equals(other.interviewID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rps.entities.Interview[interviewID=" + interviewID + "]";
    }

}
