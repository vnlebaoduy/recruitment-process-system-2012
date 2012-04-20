/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import rps.business.InterviewService;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
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
}
