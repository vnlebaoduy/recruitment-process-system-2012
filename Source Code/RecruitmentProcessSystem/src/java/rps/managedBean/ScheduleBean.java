/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rps.business.InterviewService;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ScheduleBean implements Serializable {

    private InterviewService interviewService;

    /** Creates a new instance of ScheduleBean */
    public ScheduleBean() {
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="applicant-info.xhtml | vacancy-info.xhtml">
    private Interview interview;

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
    // </editor-fold>
}
