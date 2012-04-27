/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import rps.business.EmployeeService;
import rps.business.InterviewService;
import rps.entities.Account;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class InterviewEditorBean implements Serializable {

    private InterviewService interviewService;

    /** Creates a new instance of InterviewEditorBean */
    public InterviewEditorBean() {
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="processing-interview.xhtml | conflict-time-dialog">
    // <editor-fold defaultstate="collapsed" desc="EDIT INTERVIEW">
    private Interview interview;

    public Interview getInterview() {
        if (interview == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String paramID = request.getParameter("id");
            String paramType = request.getParameter("type");
            if (paramID != null && !paramID.equals("")) {
                interview = interviewService.getInterview(paramID);
            }
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
    private Date date;

    public Date getDate() {
        if (date == null) {
            date = interview.getStartedTime();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    private String employeeID;

    public String getEmployeeID() {
        if (employeeID == null) {
            employeeID = interview.getEmployee().getEmployeeID();
        }
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    private boolean iConflict;

    public boolean isiConflict() {
        return iConflict;
    }
    private boolean aConflict;

    public boolean isaConflict() {
        return aConflict;
    }
    private List<Interview> listConflict;

    public List<Interview> getListConflict() {
        if (listConflict == null) {
            listConflict = new ArrayList<Interview>();
        }
        return listConflict;
    }

    public boolean validate() {
        try {
            EmployeeService employeeService = new EmployeeService();
            Employee employeeValue = employeeService.getEmployeeInfo(getEmployeeID());
            Applicant applicantValue = interview.getApplicant();
            Date startValue = formatDate(getDate(), interview.getStartedTime());
            Date endValue = formatDate(getDate(), interview.getEndedTime());

            interview.setStartedTime(startValue);
            interview.setEndedTime(endValue);
            if (employeeValue == null) {
                return false;
            } else {
                interview.setEmployee(employeeValue);
            }
            if (employeeValue != null) {
                List<Interview> results = interviewService.getInterviews(
                        employeeValue, startValue, endValue);
                if (results.size() > 0) {
                    for (Interview i : results) {
                        if (!i.equals(interview)) {
                            getListConflict().add(i);
                        }
                    }
                    if (listConflict != null && !listConflict.isEmpty()) {
                        iConflict = true;
                        String content = employeeValue.getFirstName() + " "
                                + employeeValue.getLastName() + " had " + results.size()
                                + " interviews scheduled in this time";
                        FacesMessage message = new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                content,
                                content);
                        FacesContext.getCurrentInstance().addMessage("frm-edit-interview:cboInterviewer", message);
                        message = new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                "WARNING",
                                "Time has been conflicted");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return false;
                    }
                }
                if (applicantValue != null) {
                    results = interviewService.getInterviews(
                            applicantValue, startValue, endValue);
                    if (results.size() > 0) {
                        for (Interview i : results) {
                            if (!i.equals(interview)) {
                                getListConflict().add(i);
                            }
                        }
                        if (listConflict != null && !listConflict.isEmpty()) {
                            aConflict = true;
                            String content = applicantValue.getFirstName() + " "
                                    + applicantValue.getLastName() + " had " + results.size()
                                    + " interviews scheduled in this time";
                            FacesMessage message = new FacesMessage(
                                    FacesMessage.SEVERITY_WARN,
                                    content,
                                    content);
                            FacesContext.getCurrentInstance().addMessage("frm-edit-interview:cboApplicant", message);
                            message = new FacesMessage(
                                    FacesMessage.SEVERITY_WARN,
                                    "WARNING",
                                    "Time has been conflicted");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                            return false;
                        }
                    }
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
            FacesContext.getCurrentInstance().addMessage("frm-edit-interview:endTime", message);
            return false;
        }
        return true;
    }

    public String edit() {
        try {
            if (validateTime() && validate()) {
                interviewService.beginTransaction();
                interviewService.updateInterview(interview.getInterviewID(),
                        interview.getEmployee(), interview.getVacancy(),
                        interview.getApplicant(), interview.getStartedTime(),
                        interview.getEndedTime(), 0, interview.getAVStatus());
                interviewService.commitTransaction();
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "SUCCESS",
                        "Interview has been updated");
                FacesContext.getCurrentInstance().addMessage(null, message);
                resetForm();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="DISPLAY CONFLICT">

    public void showConflictDialog() {
        Map<String, Object> params =
                FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        ConflictTimeBean bean = (ConflictTimeBean) params.get("conflictTimeBean");
        if (bean != null) {
            bean.setMsgConflict(getMsgConflict());
            bean.setInterview(interview);
            bean.setListConflict(listConflict);
            listConflict = null;
            aConflict = false;
            iConflict = false;
        }
    }
    private String msgConflict;

    public String getMsgConflict() {
        int num = 0;
        num = getListConflict().size();
        String name = "";
        if (iConflict) {
            name = interview.getEmployee().getFirstName() + " "
                    + interview.getEmployee().getLastName();
        } else if (aConflict) {
            name = interview.getApplicant().getFirstName() + " "
                    + interview.getApplicant().getLastName();
        }
        switch (num) {
            case 0:
                msgConflict = "";
                break;
            case 1:
                msgConflict = "There are 1 interview from "
                        + timeFormat(interview.getStartedTime()) + " to "
                        + timeFormat(interview.getEndedTime()) + " scheduled for "
                        + name;
                break;
            default:
                msgConflict = "There are " + num + " interviews from "
                        + timeFormat(interview.getStartedTime()) + " to "
                        + timeFormat(interview.getEndedTime()) + " scheduled for "
                        + name;
                break;
        }
        return msgConflict;
    }

    public String timeFormat(Object obj) {
        return format(obj, "HH:mm");
    }

    private String format(Object obj, String pattern) {
        try {
            Date d = (Date) obj;
            Format formatter = new SimpleDateFormat(pattern);
            return formatter.format(d);
        } catch (Exception ex) {
            return "";
        }
    }

    private void resetForm() {
        setInterview(null);
        setEmployeeID(null);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="OTHER INTERVIEWS">
    private List<Interview> otherInterview;

    public List<Interview> getOtherInterviews() {
        if (otherInterview == null) {
            otherInterview = new ArrayList<Interview>();
            if (getInterview() != null) {
                AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().get("accountMB");
                if (bean != null) {
                    if (bean.isInterviewer()) {
                        otherInterview = interviewService.getCurrentInterviews(
                                bean.getAccount().getEmployee());
                    } else if (bean.ishRGroup()) {
                        otherInterview = interviewService.getRejectedInterviews();
                    }
                    otherInterview.remove(getInterview());
                }
            }
        }
        return otherInterview;
    }
    // </editor-fold>
    // </editor-fold>
}
