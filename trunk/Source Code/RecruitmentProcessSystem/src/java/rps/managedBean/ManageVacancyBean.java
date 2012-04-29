/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import rps.business.ApplicantService;
import rps.business.InterviewService;
import rps.business.VacancyService;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ManageVacancyBean implements Serializable {

    private VacancyService vacancyService;

    /** Creates a new instance of ManageVacancyBean */
    public ManageVacancyBean() {
        vacancyService = new VacancyService();
    }
    // <editor-fold defaultstate="collapsed" desc="vacancies.xhtml">
    // <editor-fold defaultstate="collapsed" desc="SEARCH VACANCY">
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
    private List<Vacancy> vacancies;

    public List<Vacancy> getVacancies() {
        vacancies = vacancyService.searchVacancy(keyword, status);
        if (vacancies == null) {
            vacancies = new ArrayList<Vacancy>();
        }
        return vacancies;
    }

    public void search() {
        try {
            vacancies = vacancyService.searchVacancy(keyword, status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String msgSearch;

    public String getMsgSearch() {
        int num = 0;
        msgSearch = "";
        if (getVacancies() != null && !keyword.equals("")) {
            num = getVacancies().size();
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
    // <editor-fold defaultstate="collapsed" desc="MANAGE VACANCY">
    public int numberHired(String id) {
        try {
            return vacancyService.getApplicantHired(id).size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void suspend(String id) {
        Vacancy vacancy = vacancyService.getDetailVacancy(id);
        vacancy.setStatus(99);
        editVacancy(vacancy);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "WARNING", "The vacancy has been suspended");
        facesContext.addMessage(null, message);
    }

    public void close(String id) {
        try {
            Vacancy vacancy = vacancyService.getDetailVacancy(id);
            vacancy.setStatus(1);
            editVacancy(vacancy);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION", "The vacancy has been closed");
            facesContext.addMessage(null, message);
            InterviewService interviewService = new InterviewService();
            ApplicantService applicantService = new ApplicantService();
            List<Interview> removeList = interviewService.getInterviews(
                    vacancy, 99);
            for (Interview i : removeList) {
                i.setStatus(1);
                i.setAVStatus(-100);
                interviewService.beginTransaction();
                interviewService.updateInterview(i.getInterviewID(),
                        i.getEmployee(), i.getVacancy(), i.getApplicant(),
                        i.getStartedTime(), i.getEndedTime(), i.getStatus(), i.getAVStatus());
                interviewService.commitTransaction();
                applicantService.checkAvailabeInterviews(i.getApplicant());
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION", "All of the related interviews have been REMOVED");
            facesContext.addMessage(null, message);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reopen(String id) {
        Vacancy vacancy = vacancyService.getDetailVacancy(id);
        vacancy.setStatus(0);
        editVacancy(vacancy);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "INFORMATION", "The vacancy has been reopened");
        facesContext.addMessage(null, message);
    }

    private void editVacancy(Vacancy vacancy) {
        try {
            vacancyService.beginTransaction();
            vacancyService.editVacancy(vacancy.getVacancyID(),
                    vacancy.getTitle(), vacancy.getDepartment(),
                    vacancy.getNumberRequirement(), vacancy.getStatus(),
                    vacancy.getPosition(), vacancy.getWorkingPlace(),
                    vacancy.getWorkType(), vacancy.getMinimumSalary(),
                    vacancy.getMaximumSalary(), vacancy.getDescription(),
                    vacancy.getSkillRequirement(), vacancy.getEntitlement(),
                    vacancy.getMinimumAge(), vacancy.getMaximumAge(),
                    vacancy.getGender(), vacancy.getDegree(),
                    vacancy.getYearOfExperience(), vacancy.getProbationaryPeriod(),
                    vacancy.getDeadline(), vacancy.getCreatedDate());
            vacancyService.commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    // </editor-fold>
}
