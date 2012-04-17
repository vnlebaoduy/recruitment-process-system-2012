/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import rps.business.ApplicantService;
import rps.business.EmployeeService;
import rps.business.ScheduleService;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Schedule;

/**
 *
 * @author Bach Luong
 */
@ManagedBean
@RequestScoped
public class ScheduleMB {

    private ScheduleService scheduleService;

    /** Creates a new instance of ScheduleMB */
    public ScheduleMB() {
        scheduleService = new ScheduleService();
    }

    public String createScheduleInterview() {
        scheduleService = new ScheduleService();
        String flag = checkConditions(startedTime, endedTime, scheduleService, employeeID, applicantID);
        if (!flag.equals("")) {
            errors = flag;
            return null;
        }
        Employee empl = new Employee(this.getEmployeeID());
        Applicant appl = new Applicant(this.getApplicantID());
        Schedule addEntity = new Schedule();
        addEntity.setEmployee(empl);
        addEntity.setApplicant(appl);
        addEntity.setStartedTime(this.getStartedTime());
        addEntity.setEndedTime(this.getEndedTime());
        scheduleService.beginTransaction();
        scheduleService.addSchedule(addEntity);
        scheduleService.commitTransaction();
        return "Schedule.xhtml";
    }

    public String checkConditions(Date startTime, Date endTime, ScheduleService ScheduleService, String emplID, String applID) {
        String flag = "";
        flag = ScheduleService.checkHour(startTime, endTime, emplID, applID);
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

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public void setScheduleService(ScheduleService ScheduleService) {
        this.scheduleService = ScheduleService;
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
    private Schedule schedule;

    public Schedule getSchedule() {
        if (schedule == null) {
            schedule = new Schedule();
        }
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int numberInterviews(Object obj) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                return scheduleService.getSchedules(date).size();
            }
            return 0;
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
            return -1;
        }
    }

    public int getNumberInterviewsOnDay() {
        try {
            if (this.getSelectedDate() != null) {
                return scheduleService.getSchedules(this.getSelectedDate()).size();
            }
            return 0;
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
            return -1;
        }
    }

    public int numberVacancies(Object obj) {
        try {
            Applicant app = (Applicant) obj;
            if (app != null) {
                return app.getVacancyList().size();
            }
            return 0;
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
            return -1;
        }
    }

    public String statusValue(Object obj) {
        try {
            int status = (Integer) obj;
            String value = "";
            switch (status) {
                case 0:
                    value = "Not in progress";
                    break;
                case 100:
                    value = "Selected";
                    break;
                case -100:
                    value = "Rejected";
                    break;
                default:
                    break;
            }
            return value;
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
            return null;
        }
    }
    private List<Schedule> lstInterviews;
    private Date selectedDate;

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<Schedule> getLstInterviews() {
        if (lstInterviews == null) {
            lstInterviews = new ArrayList<Schedule>();
        }
        return lstInterviews;
    }

    public void displayInterviews(Object obj) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                lstInterviews = scheduleService.getSchedules(date);
                this.setSelectedDate(date);
            } else {
                lstInterviews = new ArrayList<Schedule>();
            }
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
        }
    }
}
