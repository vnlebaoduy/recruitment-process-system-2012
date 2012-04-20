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
import javax.persistence.ManyToOne;
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
@Table(name = "Vacancy")
@NamedQueries({
    @NamedQuery(name = "Vacancy.findAll", query = "SELECT v FROM Vacancy v"),
    @NamedQuery(name = "Vacancy.findByVacancyID", query = "SELECT v FROM Vacancy v WHERE v.vacancyID = :vacancyID"),
    @NamedQuery(name = "Vacancy.findByTitle", query = "SELECT v FROM Vacancy v WHERE v.title = :title"),
    @NamedQuery(name = "Vacancy.findByCreatedDate", query = "SELECT v FROM Vacancy v WHERE v.createdDate = :createdDate"),
    @NamedQuery(name = "Vacancy.findByStatus", query = "SELECT v FROM Vacancy v WHERE v.status = :status"),
    @NamedQuery(name = "Vacancy.findByNumberRequirement", query = "SELECT v FROM Vacancy v WHERE v.numberRequirement = :numberRequirement"),
    @NamedQuery(name = "Vacancy.findByPosition", query = "SELECT v FROM Vacancy v WHERE v.position = :position"),
    @NamedQuery(name = "Vacancy.findByWorkingPlace", query = "SELECT v FROM Vacancy v WHERE v.workingPlace = :workingPlace"),
    @NamedQuery(name = "Vacancy.findByWorkType", query = "SELECT v FROM Vacancy v WHERE v.workType = :workType"),
    @NamedQuery(name = "Vacancy.findByMinimumSalary", query = "SELECT v FROM Vacancy v WHERE v.minimumSalary = :minimumSalary"),
    @NamedQuery(name = "Vacancy.findByMaximumSalary", query = "SELECT v FROM Vacancy v WHERE v.maximumSalary = :maximumSalary"),
    @NamedQuery(name = "Vacancy.findByDescription", query = "SELECT v FROM Vacancy v WHERE v.description = :description"),
    @NamedQuery(name = "Vacancy.findBySkillRequirement", query = "SELECT v FROM Vacancy v WHERE v.skillRequirement = :skillRequirement"),
    @NamedQuery(name = "Vacancy.findByEntitlement", query = "SELECT v FROM Vacancy v WHERE v.entitlement = :entitlement"),
    @NamedQuery(name = "Vacancy.findByMinimumAge", query = "SELECT v FROM Vacancy v WHERE v.minimumAge = :minimumAge"),
    @NamedQuery(name = "Vacancy.findByMaximumAge", query = "SELECT v FROM Vacancy v WHERE v.maximumAge = :maximumAge"),
    @NamedQuery(name = "Vacancy.findByGender", query = "SELECT v FROM Vacancy v WHERE v.gender = :gender"),
    @NamedQuery(name = "Vacancy.findByDegree", query = "SELECT v FROM Vacancy v WHERE v.degree = :degree"),
    @NamedQuery(name = "Vacancy.findByYearOfExperience", query = "SELECT v FROM Vacancy v WHERE v.yearOfExperience = :yearOfExperience"),
    @NamedQuery(name = "Vacancy.findByProbationaryPeriod", query = "SELECT v FROM Vacancy v WHERE v.probationaryPeriod = :probationaryPeriod"),
    @NamedQuery(name = "Vacancy.findByDeadline", query = "SELECT v FROM Vacancy v WHERE v.deadline = :deadline")})
public class Vacancy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VacancyID")
    private String vacancyID;
    @Basic(optional = false)
    @Column(name = "Title")
    private String title;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "NumberRequirement")
    private Integer numberRequirement;
    @Column(name = "Position")
    private String position;
    @Column(name = "WorkingPlace")
    private String workingPlace;
    @Column(name = "WorkType")
    private String workType;
    @Column(name = "MinimumSalary")
    private Double minimumSalary;
    @Column(name = "MaximumSalary")
    private Double maximumSalary;
    @Column(name = "Description")
    private String description;
    @Column(name = "SkillRequirement")
    private String skillRequirement;
    @Column(name = "Entitlement")
    private String entitlement;
    @Column(name = "MinimumAge")
    private Integer minimumAge;
    @Column(name = "MaximumAge")
    private Integer maximumAge;
    @Column(name = "Gender")
    private Boolean gender;
    @Column(name = "Degree")
    private String degree;
    @Column(name = "YearOfExperience")
    private Integer yearOfExperience;
    @Column(name = "ProbationaryPeriod")
    private String probationaryPeriod;
    @Column(name = "Deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @JoinColumn(name = "DepartmentID", referencedColumnName = "DepartmentID")
    @ManyToOne(optional = false)
    private Department department;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacancy")
    private List<Interview> interviewList;

    public Vacancy() {
    }

    public Vacancy(String vacancyID) {
        this.vacancyID = vacancyID;
    }

    public Vacancy(String vacancyID, String title) {
        this.vacancyID = vacancyID;
        this.title = title;
    }

    public String getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(String vacancyID) {
        this.vacancyID = vacancyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNumberRequirement() {
        return numberRequirement;
    }

    public void setNumberRequirement(Integer numberRequirement) {
        this.numberRequirement = numberRequirement;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillRequirement() {
        return skillRequirement;
    }

    public void setSkillRequirement(String skillRequirement) {
        this.skillRequirement = skillRequirement;
    }

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getProbationaryPeriod() {
        return probationaryPeriod;
    }

    public void setProbationaryPeriod(String probationaryPeriod) {
        this.probationaryPeriod = probationaryPeriod;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Interview> getInterviewList() {
        return interviewList;
    }

    public void setInterviewList(List<Interview> interviewList) {
        this.interviewList = interviewList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vacancyID != null ? vacancyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacancy)) {
            return false;
        }
        Vacancy other = (Vacancy) object;
        if ((this.vacancyID == null && other.vacancyID != null) || (this.vacancyID != null && !this.vacancyID.equals(other.vacancyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rps.entities.Vacancy[vacancyID=" + vacancyID + "]";
    }

}
