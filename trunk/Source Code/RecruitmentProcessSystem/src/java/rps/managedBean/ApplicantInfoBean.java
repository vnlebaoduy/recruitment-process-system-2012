/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
public class ApplicantInfoBean implements Serializable{

    private VacancyService vacancyService;
    private ApplicantService applicantService;
    private InterviewService interviewService;

    /** Creates a new instance of ApplicantInfoBean */
    public ApplicantInfoBean() {
        vacancyService = new VacancyService();
        applicantService = new ApplicantService();
        interviewService = new InterviewService();
    }
    // <editor-fold defaultstate="collapsed" desc="applicant-info.xhtml">
    // <editor-fold defaultstate="collapsed" desc="APPLICANT INFORMATION">
    private Applicant applicant;

    public Applicant getApplicant() {
        try {
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id = params.get("id");
            if (id != null && !id.equals("")) {
                applicant = applicantService.getApplicant(id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return applicant;
    }
    private List<Interview> interviews;

    public List<Interview> getInterviews() {
        if (getApplicant() != null) {
            interviews = interviewService.getInterviews(applicant);
        }
        return interviews;
    }

    public void viewSchedule() {
        try {
            Map<String, Object> params =
                    FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            ScheduleBean bean = (ScheduleBean) params.get("scheduleBean");
            if (bean != null) {
                Map<String, String> params2 =
                        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                String id = params2.get("iid");
                bean.setInterview(interviewService.getInterview(id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
}
