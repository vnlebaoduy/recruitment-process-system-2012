/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

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
import rps.business.ApplicantService;
import rps.business.EmployeeService;
import rps.business.InterviewService;
import rps.business.VacancyService;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class InterviewBean {

    private InterviewService interviewService;

    /** Creates a new instance of InterviewBean */
    public InterviewBean() {
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="info.xhtml | progress-interview-dialog.xhtml">
    // <editor-fold defaultstate="collapsed" desc="CURRENT SCHEDULE">

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
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="manage-interview.xhtml">
    // <editor-fold defaultstate="collapsed" desc="MANAGE INTERVIEW">

    private int numberInterviews(Object obj) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                return interviewService.getInterviews(date).size();
            }
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public String msgEvent(Object obj) {
        String msgEvent = "";
        int num = numberInterviews(obj);
        if (num == 1) {
            msgEvent = "1 Event";
        } else if (num > 1) {
            msgEvent = num + " Events";
        }
        return msgEvent;
    }
    // </editor-fold>
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="interviews.xhtml">
    // <editor-fold defaultstate="collapsed" desc="DISPLAY ALL INTERVIEWS">
    private Date selectedDate;

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
    private List<Interview> lstInterviews;

    public List<Interview> getLstInterviews() {
        if (lstInterviews == null) {
            lstInterviews = new ArrayList<Interview>();
        }
        return lstInterviews;
    }

    public void displayInterviews(Object obj) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                lstInterviews = interviewService.getInterviews(date);
                setSelectedDate(date);
            } else {
                lstInterviews = new ArrayList<Interview>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String msgNumber;

    public String getMsgNumber() {
        int num = getLstInterviews().size();
        switch (num) {
            case 0:
            case 1:
                msgNumber = "There are currently " + num + " interview scheduled.";
                break;
            default:
                msgNumber = "There are currently " + num + " inteviews scheduled.";
                break;
        }
        return msgNumber;
    }
    // </editor-fold>
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="interview.xhtml | conflict-time-dialog">
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
    private List<Vacancy> vacancies;

    public List<Vacancy> getVacancies() {
        try {
            if (vacancies == null) {
                ApplicantService applicantService = new ApplicantService();
                Applicant applicant = applicantService.getApplicant(getApplicantID());
                if (applicant != null) {
                    vacancies = interviewService.getVacancies(applicant);
                } else {
                    vacancies = new ArrayList<Vacancy>();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vacancies;
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
        if (applicantID == null) {
            if (!getApplicantsNotScheduled().isEmpty()) {
                applicantID = getApplicantsNotScheduled().get(0).getApplicantID();
            } else {
                applicantID = "";
            }
        }
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
    private String vacancyID;

    public String getVacancyID() {
        if (vacancyID == null) {
            vacancyID = "";
        }
        return vacancyID;
    }

    public void setVacancyID(String vacancyID) {
        this.vacancyID = vacancyID;
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
            ApplicantService applicantService = new ApplicantService();
            VacancyService vacancyService = new VacancyService();
            Employee employeeValue = employeeService.getEmployeeInfo(getEmployeeID());
            Applicant applicantValue = applicantService.getApplicant(getApplicantID());
            Vacancy vacancyValue = vacancyService.getDetailVacancy(getVacancyID());
            Date startValue = formatDate(date, interview.getStartedTime());
            Date endValue = formatDate(date, interview.getEndedTime());

            interview.setStartedTime(startValue);
            interview.setEndedTime(endValue);
            if (employeeValue == null) {
                return false;
            } else {
                interview.setEmployee(employeeValue);
            }
            if (applicantValue == null) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "WARNING",
                        "Have not an applicant for scheduling interview.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return false;
            } else {
                interview.setApplicant(applicantValue);
            }
            if (vacancyValue == null) {
                return false;
            } else {
                interview.setVacancy(vacancyValue);
            }
            if (employeeValue != null) {
                List<Interview> results = interviewService.getInterviews(
                        employeeValue, startValue, endValue);
                if (results.size() > 0) {
                    iConflict = true;
                    listConflict = results;
                    String content = employeeValue.getFirstName() + " "
                            + employeeValue.getLastName() + " had " + results.size()
                            + " interviews scheduled in this time";
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            content,
                            content);
                    FacesContext.getCurrentInstance().addMessage("frm-add-interview:cboInterviewer", message);
                    message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "WARNING",
                            "Time has been conflicted");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return false;
                } else if (applicantValue != null) {
                    results = interviewService.getInterviews(
                            applicantValue, startValue, endValue);
                    if (results.size() > 0) {
                        aConflict = true;
                        listConflict = results;
                        String content = applicantValue.getFirstName() + " "
                                + applicantValue.getLastName() + " had " + results.size()
                                + " interviews scheduled in this time";
                        FacesMessage message = new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                content,
                                content);
                        FacesContext.getCurrentInstance().addMessage("frm-add-interview:cboApplicant", message);
                        message = new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                "WARNING",
                                "Time has been conflicted");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return false;
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
            FacesContext.getCurrentInstance().addMessage("frm-add-interview:endTime", message);
            return false;
        }
        return true;
    }

    public String add() {
        try {
            if (validateTime() && validate()) {
                Interview editedInterview = interviewService.getInterviews(
                        interview.getApplicant(), interview.getVacancy(), 0, 0);
                if (editedInterview != null) {
                    interviewService.beginTransaction();
                    interviewService.updateInterview(editedInterview.getInterviewID(),
                            interview.getEmployee(), editedInterview.getVacancy(),
                            editedInterview.getApplicant(), interview.getStartedTime(),
                            interview.getEndedTime(), 0, 99);
                    interviewService.commitTransaction();
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "SUCCESS",
                            "Interview has been scheduled.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    resetForm();
                } else {
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "ERROR",
                            "Interview not found");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
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
        applicantsNotScheduled = null;
        vacancies = null;
        setInterview(null);
        setApplicantID(null);
        setEmployeeID(null);
        setVacancyID(null);
    }
    // </editor-fold>
    // </editor-fold>
}
