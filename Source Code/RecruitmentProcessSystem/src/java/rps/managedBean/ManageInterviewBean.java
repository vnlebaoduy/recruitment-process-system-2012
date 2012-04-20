/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.event.DateSelectEvent;
import rps.business.ApplicantService;
import rps.business.EmployeeService;
import rps.business.InterviewService;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class ManageInterviewBean {

    private InterviewService interviewService;

    /** Creates a new instance of ManageInterviewBean */
    public ManageInterviewBean() {
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="manage-interview.xhtml">
    // <editor-fold defaultstate="collapsed" desc="MANAGE INTERVIEW">
    private List<Interview> interviews;

    public List<Interview> getInterviews() {
        return interviews;
    }

    public List<Interview> listInterviews(Object obj) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                interviews = interviewService.getInterviews(date);
                return interviews;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void postpone(String id) {
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(99);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Postpone interview", "The interview has been postponed.");
        facesContext.addMessage(null, message);
    }

    public void remove(String id) {
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(1);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Remove interview", "The interview has been removed.");
        facesContext.addMessage(null, message);
    }

    public void reopen(String id) {
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(0);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reopen interview", "The interview has been reopened.");
        facesContext.addMessage(null, message);
    }

    private void editInterview(Interview interview) {
        try {
            interviewService.beginTransaction();
            interviewService.updateInterview(interview.getInterviewID(),
                    interview.getEmployee(), interview.getVacancy(),
                    interview.getApplicant(), interview.getStartedTime(),
                    interview.getEndedTime(), interview.getStatus(), interview.getAVStatus());
            interviewService.commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="interview.xhtml">
    // <editor-fold defaultstate="collapsed" desc="ADD INTERVIEW">
    private Interview interview;

    public Interview getInterview() {
        if (interview == null) {
            interview = new Interview();
        }
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
    private List<Employee> interviewers;

    public List<Employee> getInterviewers() {
        try {
            if (interviewers == null) {
                EmployeeService employeeService = new EmployeeService();
                interviewers = employeeService.getInterviewers();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return interviewers;
    }
    private List<Applicant> applicantsNotScheduled;

    public List<Applicant> getApplicantsNotScheduled() {
        try {
            if (applicantsNotScheduled == null) {
                applicantsNotScheduled = interviewService.getApplicantsByAVStatus(0);
            }
            return applicantsNotScheduled;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return applicantsNotScheduled;
    }
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    private String applicantID;

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }
    private String employeeID;

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public boolean validate() {
        try {
            EmployeeService employeeService = new EmployeeService();
            ApplicantService applicantService = new ApplicantService();
            Date dateValue = date;
            Employee employeeValue = employeeService.getEmployeeInfo(employeeID);
            Applicant applicantValue = applicantService.getApplicant(applicantID);
            Date startValue = formatDate(date, interview.getStartedTime());
            Date endValue = formatDate(date, interview.getEndedTime());

            if (employeeValue != null) {
                List<Interview> results = interviewService.getInterviews(
                        employeeValue, startValue, endValue);
                if (results.size() > 0) {
                    String content = employeeValue.getFirstName() + " "
                            + employeeValue.getLastName() + " had " + results.size()
                            + " interviews scheduled in this time";
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            content,
                            content);
                    FacesContext.getCurrentInstance().addMessage("frm-add-interview:cboInterviewer", message);
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private Date formatDate(Date date, Date time) {
        Format formatter = new SimpleDateFormat("MM/dd/yy");
        String dValue = formatter.format(date);
        formatter = new SimpleDateFormat("HH:mm");
        String tValue = formatter.format(time);
        return new Date(dValue + " " + tValue);
    }

    public boolean validateTime() {
        Date start = interview.getStartedTime();
        Date end = interview.getEndedTime();
        if (end.before(start) || end.equals(start)) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Time must be greater than started time",
                    "Time must be greater than started time");
            FacesContext.getCurrentInstance().addMessage("frm-add-interview:endTime", message);
            return false;
        }
        return true;
    }

    public void add() {
        try {
            if (validateTime() && validate()) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
}
