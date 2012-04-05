/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "Applicant")
@NamedQueries({
    @NamedQuery(name = "Applicant.findAll", query = "SELECT a FROM Applicant a"),
    @NamedQuery(name = "Applicant.findByApplicantID", query = "SELECT a FROM Applicant a WHERE a.applicantID = :applicantID"),
    @NamedQuery(name = "Applicant.findByFirstName", query = "SELECT a FROM Applicant a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Applicant.findByLastName", query = "SELECT a FROM Applicant a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Applicant.findByGender", query = "SELECT a FROM Applicant a WHERE a.gender = :gender"),
    @NamedQuery(name = "Applicant.findByDob", query = "SELECT a FROM Applicant a WHERE a.dob = :dob"),
    @NamedQuery(name = "Applicant.findByPhoneNumber", query = "SELECT a FROM Applicant a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Applicant.findByEmail", query = "SELECT a FROM Applicant a WHERE a.email = :email"),
    @NamedQuery(name = "Applicant.findByAddress", query = "SELECT a FROM Applicant a WHERE a.address = :address"),
    @NamedQuery(name = "Applicant.findByStatus", query = "SELECT a FROM Applicant a WHERE a.status = :status"),
    @NamedQuery(name = "Applicant.findBySalaryRequirement", query = "SELECT a FROM Applicant a WHERE a.salaryRequirement = :salaryRequirement"),
    @NamedQuery(name = "Applicant.findByLanguage", query = "SELECT a FROM Applicant a WHERE a.language = :language"),
    @NamedQuery(name = "Applicant.findByYearOfExperience", query = "SELECT a FROM Applicant a WHERE a.yearOfExperience = :yearOfExperience"),
    @NamedQuery(name = "Applicant.findByDegree", query = "SELECT a FROM Applicant a WHERE a.degree = :degree"),
    @NamedQuery(name = "Applicant.findByCreatedDate", query = "SELECT a FROM Applicant a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Applicant.findBySkill", query = "SELECT a FROM Applicant a WHERE a.skill = :skill"),
    @NamedQuery(name = "Applicant.findByAward", query = "SELECT a FROM Applicant a WHERE a.award = :award")})
public class Applicant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ApplicantID")
    private String applicantID;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Gender")
    private Boolean gender;
    @Column(name = "DOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Column(name = "Address")
    private String address;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "SalaryRequirement")
    private Double salaryRequirement;
    @Column(name = "Language")
    private String language;
    @Column(name = "YearOfExperience")
    private Integer yearOfExperience;
    @Column(name = "Degree")
    private String degree;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "Skill")
    private String skill;
    @Column(name = "Award")
    private String award;
    @JoinTable(name = "VacancyOfApplicant", joinColumns = {
        @JoinColumn(name = "ApplicantID", referencedColumnName = "ApplicantID")}, inverseJoinColumns = {
        @JoinColumn(name = "VacancyID", referencedColumnName = "VacancyID")})
    @ManyToMany
    private List<Vacancy> vacancyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicant")
    private List<SchedulingInterview> schedulingInterviewList;

    public Applicant() {
    }

    public Applicant(String applicantID) {
        this.applicantID = applicantID;
    }

    public Applicant(String applicantID, String firstName, String lastName, String email) {
        this.applicantID = applicantID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getSalaryRequirement() {
        return salaryRequirement;
    }

    public void setSalaryRequirement(Double salaryRequirement) {
        this.salaryRequirement = salaryRequirement;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

    public List<SchedulingInterview> getSchedulingInterviewList() {
        return schedulingInterviewList;
    }

    public void setSchedulingInterviewList(List<SchedulingInterview> schedulingInterviewList) {
        this.schedulingInterviewList = schedulingInterviewList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicantID != null ? applicantID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Applicant)) {
            return false;
        }
        Applicant other = (Applicant) object;
        if ((this.applicantID == null && other.applicantID != null) || (this.applicantID != null && !this.applicantID.equals(other.applicantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rps.entities.Applicant[applicantID=" + applicantID + "]";
    }

}
