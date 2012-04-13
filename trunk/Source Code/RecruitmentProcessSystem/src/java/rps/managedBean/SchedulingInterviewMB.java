/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import rps.business.ApplicantService;
import rps.business.EmployeeService;
import rps.business.SchedulingInterviewService;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.SchedulingInterview;

/**
 *
 * @author Bach Luong
 */
@ManagedBean
@SessionScoped
public class SchedulingInterviewMB {

    private SchedulingInterviewService schedulingInterviewService;

    /** Creates a new instance of SchedulingInterviewMB */
    public SchedulingInterviewMB() {
        schedulingInterviewService = new SchedulingInterviewService();
    }

    public String createScheduleInterview() {
        schedulingInterviewService = new SchedulingInterviewService();
        Boolean flag = checkHour(startedTime, endedTime, schedulingInterviewService);
        if (flag == false) {
            errors = "Datetime không hợp lệ!";
            return null;
        }
        Employee empl = new Employee(this.getEmployeeID());
        Applicant appl = new Applicant(this.getApplicantID());
        SchedulingInterview addEntity = new SchedulingInterview();
        addEntity.setEmployee(empl);
        addEntity.setApplicant(appl);
        addEntity.setStartedTime(this.getStartedTime());
        addEntity.setEndedTime(this.getEndedTime());
        schedulingInterviewService.beginTransaction();
        schedulingInterviewService.addSchedulingInterview(addEntity);
        schedulingInterviewService.commitTransaction();
        return "schedulingInterview.xhtml";
    }

    public Boolean checkHour(Date startTime, Date endTime, SchedulingInterviewService schedulingInterviewService) {
        Boolean flag = false;
        flag = schedulingInterviewService.checkHour(startTime, endTime);
        return flag;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Date getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(Date endedTime) {
        this.endedTime = endedTime;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
     public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<SelectItem> getLstSlItemApplicant() {
        ApplicantService srv = new ApplicantService();
        List<SelectItem> lstItem = new ArrayList<SelectItem>();
        SelectItem item;
        Applicant applicantEntity;
        List<Applicant> lstApplicant = srv.getListApplicant();
        for (int count = 0; count < lstApplicant.size(); count++) {
            applicantEntity = lstApplicant.get(count);
            item = new SelectItem(applicantEntity.getApplicantID(), applicantEntity.getFirstName() + " " + applicantEntity.getLastName());
            lstItem.add(item);
        }
        return lstItem;
    }

    public void setLstSlItemApplicant(List<SelectItem> lstSlItemApplicant) {
        this.lstSlItemApplicant = lstSlItemApplicant;
    }

    public List<SelectItem> getLstSlItemEmpl() {
        EmployeeService srv = new EmployeeService();
        List<SelectItem> lstItem = new ArrayList<SelectItem>();
        SelectItem item;
        Employee employeeEntity;
        List<Employee> lstEmployee = srv.getLstEmpl();
        for (int count = 0; count < lstEmployee.size(); count++) {
            employeeEntity = lstEmployee.get(count);
            item = new SelectItem(employeeEntity.getEmployeeID(), employeeEntity.getFirstName() + " " + employeeEntity.getLastName());
            lstItem.add(item);
        }
        return lstItem;
    }

    public void setLstSlItemEmpl(List<SelectItem> lstSlItemEmpl) {
        this.lstSlItemEmpl = lstSlItemEmpl;


    }
    private List<SelectItem> lstSlItemEmpl;
    private List<SelectItem> lstSlItemApplicant;
    private Employee employee;
    private Applicant applicant;
    private Date endedTime;
    private Date startedTime;
    private Integer status;
    private String employeeID;
    private String applicantID;
    private String errors;
}
