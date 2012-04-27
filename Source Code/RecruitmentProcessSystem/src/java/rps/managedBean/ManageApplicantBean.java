/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rps.business.ApplicantService;
import rps.business.InterviewService;
import rps.entities.Applicant;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ManageApplicantBean {

    private ApplicantService applicantService;

    /** Creates a new instance of ManageApplicantBean */
    public ManageApplicantBean() {
        applicantService = new ApplicantService();
    }
    // <editor-fold defaultstate="collapsed" desc="applicants.xhtml">
    // <editor-fold defaultstate="collapsed" desc="SEARCH APPLICANT">
    private String keyword;

    public String getKeyword() {
        if (keyword == null) {
            keyword = "";
        }
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    private List<Applicant> applicants;

    public List<Applicant> getApplicants() {
        applicants = applicantService.searchApplicant(keyword, status);
        if (applicants == null) {
            applicants = new ArrayList<Applicant>();
        }
        return applicants;
    }

    public void search() {
        try {
            applicants = applicantService.searchApplicant(keyword, status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String msgSearch;

    public String getMsgSearch() {
        int num = 0;
        msgSearch = "";
        if (getApplicants() != null && !keyword.equals("")) {
            num = getApplicants().size();
            switch (num) {
                case 0:
                    msgSearch = "There are no result for key word \"" + keyword + "\"";
                    break;
                case 1:
                    msgSearch = "There are 1 result for key word \"" + keyword + "\"";
                    break;
                default:
                    msgSearch = "There are " + num + " results for key word \"" + keyword + "\"";
                    break;
            }
        }
        return msgSearch;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="MANAGE APPLICANT">
    public void banned(String id) {
        try {
            Applicant applicant = applicantService.getApplicant(id);
            applicant.setStatus(-1);
            editApplicant(applicant);
//            InterviewService interviewService = new InterviewService();
//            List<Interview> list = interviewService.getInterviews(applicant, 100);
//            if (list != null && !list.isEmpty()) {
//                for (Interview interview : list) {
//                    interviewService.beginTransaction();
//                    interviewService.updateInterview(interview.getInterviewID(),
//                            interview.getEmployee(), interview.getVacancy(),
//                            interview.getApplicant(), interview.getStartedTime(),
//                            interview.getEndedTime(), interview.getStatus(), 1);
//                    interviewService.commitTransaction();
//                }
//            }
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION", "The applicant has been banned");
            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editApplicant(Applicant applicant) {
        try {
            applicantService.beginTransaction();
            applicantService.updateApplicant(applicant.getApplicantID(),
                    applicant.getFirstName(), applicant.getLastName(),
                    applicant.getGender(), applicant.getDob(), applicant.getPhoneNumber(),
                    applicant.getEmail(), applicant.getAddress(), applicant.getSalaryRequirement(),
                    applicant.getLanguage(), applicant.getYearOfExperience(), applicant.getDegree(),
                    applicant.getSkill(), applicant.getAward(), applicant.getInterviewList(),
                    applicant.getCreatedDate(), applicant.getStatus());
            applicantService.commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
}
