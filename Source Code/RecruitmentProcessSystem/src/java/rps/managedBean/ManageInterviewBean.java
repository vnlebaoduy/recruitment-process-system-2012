/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DateSelectEvent;
import rps.business.InterviewService;
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

    public List<Interview> listInterviews(boolean search, Object obj, int status) {
        try {
            Date date = (Date) obj;
            if (date != null) {
                if (search) {
                    if (status == -1) {
                        interviews = interviewService.getInterviews(date);
                    } else {
                        interviews = interviewService.getInterviews(date, status);
                    }
                } else {
                    interviews = interviewService.getInterviews(date);
                }
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
    // <editor-fold defaultstate="collapsed" desc="SEARCH ON INTERVIEW DATE">
    // </editor-fold>
    // </editor-fold>
}
