/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import rps.business.InterviewService;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class MyInterviewBean implements Serializable {

    private InterviewService interviewService;

    /** Creates a new instance of MyInterviewBean */
    public MyInterviewBean() {
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="info.xhtml | progress-interview-dialog.xhtml">
    // <editor-fold defaultstate="collapsed" desc="CURRENT INTERVIEW">
    private List<Interview> myCurrentInterviews;

    public List<Interview> getMyCurrentInterviews() {
        if (myCurrentInterviews == null) {
            try {
                AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().get("accountMB");
                if (bean != null) {
                    if (bean.isInterviewer()) {
                        myCurrentInterviews = interviewService.getCurrentInterviews(
                                bean.getAccount().getEmployee());
                    } else if (bean.ishRGroup()) {
                        myCurrentInterviews = interviewService.getRejectedInterviews();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return myCurrentInterviews;
    }
    private int numberMyCurrentInterview;

    public int getNumberMyCurrentInterview() {
        numberMyCurrentInterview = getMyCurrentInterviews().size();
        return numberMyCurrentInterview;
    }
    private String msgNumber;

    public String getMsgNumber() {
        int num = getNumberMyCurrentInterview();
        AccountMB bean = (AccountMB) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("accountMB");
        if (bean != null) {
            if (bean.isInterviewer()) {
                switch (num) {
                    case 0:
                        msgNumber = "You have not any currently inteview scheduled.";
                        break;
                    case 1:
                        msgNumber = "You have currently 1 inteview scheduled.";
                        break;
                    default:
                        msgNumber = "You have currently " + num + " inteviews scheduled.";
                        break;
                }
            } else if (bean.ishRGroup()) {
                switch (num) {
                    case 0:
                        msgNumber = "You have not any rejected inteview.";
                        break;
                    case 1:
                        msgNumber = "You have currently 1 rejected inteview.";
                        break;
                    default:
                        msgNumber = "You have currently " + num + " rejected inteviews.";
                        break;
                }
            }
        }
        return msgNumber;
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PROGRESS INTERVIEW">
    private Interview selectedInterview;

    public Interview getSelectedInterview() {
        if (selectedInterview == null) {
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id = params.get("id");
            selectedInterview = interviewService.getInterview(id);
            if (selectedInterview == null) {
                return new Interview();
            }
        }
        return selectedInterview;
    }

    public void progressStatus() {
        try {
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id = params.get("edit");
            if (id != null && !id.equals("")) {
                selectedInterview = interviewService.getInterview(id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeStatus() {
        try {
            interviewService.beginTransaction();
            interviewService.updateInterview(getSelectedInterview().getInterviewID(),
                    getSelectedInterview().getEmployee(),
                    getSelectedInterview().getVacancy(),
                    getSelectedInterview().getApplicant(),
                    getSelectedInterview().getStartedTime(),
                    getSelectedInterview().getEndedTime(),
                    getSelectedInterview().getStatus(),
                    getSelectedInterview().getAVStatus());
            interviewService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();
            switch (getSelectedInterview().getStatus()) {
                case 0:
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "INFORMATION", "The interview has not in progressed");
                    break;
                case 100:
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "INFORMATION", "The interview has been selected");
                    break;
                case -100:
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "WARNING", "The interview has been rejected");
                    break;
                default:
                    break;
            }
            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    //</editor-fold>
}
