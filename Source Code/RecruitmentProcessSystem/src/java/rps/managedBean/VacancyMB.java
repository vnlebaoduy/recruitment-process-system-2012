/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.managedBean;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import rps.business.VacancyService;
import rps.entities.Applicant;
import rps.entities.Department;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class VacancyMB {
private String vacancyID;
    private String title;
    private Date createdDate;
    private Integer status;
    private Integer numberRequirement;
    private String position;
    private String workingPlace;
    private String workType;
    private Double minimumSalary;
    private Double maximumSalary;
    private String description;
    private String skillRequirement;
    private String entitlement;
    private Integer minimumAge;
    private Integer maximumAge;
    private Boolean gender;
    private String degree;
    private Integer yearOfExperience;
    private String probationaryPeriod;
    private Date deadline;
    private List<Applicant> applicantList;
    private Department department;

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<Applicant> applicantList) {
        this.applicantList = applicantList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
    }

    public Double getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
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

    public String getProbationaryPeriod() {
        return probationaryPeriod;
    }

    public void setProbationaryPeriod(String probationaryPeriod) {
        this.probationaryPeriod = probationaryPeriod;
    }

    public String getSkillRequirement() {
        return skillRequirement;
    }

    public void setSkillRequirement(String skillRequirement) {
        this.skillRequirement = skillRequirement;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(String vacancyID) {
        this.vacancyID = vacancyID;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    private VacancyService vacancyService;
    /** Creates a new instance of VacancyMB */
    public VacancyMB() {
        vacancyService = new VacancyService();
    }

    public String searchVacancy(){
        list = vacancyService.searchVacancyByTitle(title);
        return "search.xhtml";
    }

    private List<Vacancy> list;

    public List<Vacancy> getList() {
        return list;
    }

    public void setList(List<Vacancy> list) {
        this.list = list;
    }
}
