/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rps.business.ApplicantService;
import rps.business.InterviewService;
import rps.business.VacancyService;
import rps.entities.Applicant;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ManageInterviewBean implements Serializable {

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

    public List<Interview> listInterviews(boolean search, Object obj, int status) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().get("accountMB");
                if (bean != null) {
                    if (bean.isInterviewer()) {
                        if (search) {
                            if (status == -1) {
                                interviews = interviewService.getInterviews(
                                        bean.getAccount().getEmployee(), date);
                            } else {
                                interviews = interviewService.getInterviews(
                                        bean.getAccount().getEmployee(), date, status);
                            }
                        } else {
                            interviews = interviewService.getInterviews(
                                    bean.getAccount().getEmployee(), date);
                        }
                    } else if (bean.ishRGroup()) {
                        if (search) {
                            if (status == -1) {
                                interviews = interviewService.getInterviews(date);
                            } else {
                                interviews = interviewService.getInterviews(date, status);
                            }
                        } else {
                            interviews = interviewService.getInterviews(date);
                        }
                    }
                }
                return interviews;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void postpone() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(99);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "WARNING", "The interview has been postponed");
        facesContext.addMessage(null, message);
    }

    public void remove() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(1);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "INFORMATION", "The interview has been removed");
        facesContext.addMessage(null, message);
    }

    public void reopen() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        Interview interview = interviewService.getInterview(id);
        interview.setStatus(0);
        editInterview(interview);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "INFORMATION", "The interview has been reopened");
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
    // <editor-fold defaultstate="collapsed" desc="REVIEW INTERVIEW">
    private Interview reviewInterview;

    public Interview getReviewInterview() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        reviewInterview = interviewService.getInterview(id);
        return reviewInterview;
    }

    public void changeStatus() {
        try {
            editInterview(reviewInterview);
            ApplicantService service = new ApplicantService();
            Applicant applicant = reviewInterview.getApplicant();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = null;
            switch (reviewInterview.getAVStatus()) {
                case 99:
                    applicant.setStatus(99);
                    break;
                case 1:
                    applicant.setStatus(1);
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "INFORMATION", "This applicant's interview is not required");
                    break;
                case 100:
                    applicant.setStatus(1);
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "INFORMATION", "This applicant will be hired");
                    break;
                case -100:
                    applicant.setStatus(0);
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "WARNING", "This applicant will be rejected");
                    break;
                default:
                    break;
            }
            if (message != null) {
                facesContext.addMessage(null, message);
            }
            interviewService.reviewInterview(reviewInterview);
            service.beginTransaction();
            service.updateApplicant(applicant.getApplicantID(),
                    applicant.getFirstName(), applicant.getLastName(),
                    applicant.getGender(), applicant.getDob(),
                    applicant.getPhoneNumber(), applicant.getEmail(),
                    applicant.getAddress(), applicant.getSalaryRequirement(),
                    applicant.getLanguage(), applicant.getYearOfExperience(),
                    applicant.getDegree(), applicant.getSkill(),
                    applicant.getAward(), applicant.getInterviewList(),
                    applicant.getCreatedDate(), applicant.getStatus());
            service.commitTransaction();
            if (applicant.getStatus() == 1) {
                VacancyService vacancyService = new VacancyService();
                boolean result = vacancyService.isClosedVacancy(reviewInterview);
                if (result) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "INFORMATION", "The number requirement is filled, the vacancy has been CLOSED");
                    facesContext.addMessage(null, message);
                }
            }
            reviewInterview = null;
            interviewService = new InterviewService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
}
