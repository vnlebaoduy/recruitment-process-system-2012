/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.validator.constraints.impl.EmailValidator;
import org.primefaces.event.DragDropEvent;
import rps.business.ApplicantService;
import rps.business.VacancyService;
import rps.entities.Applicant;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ApplicantMB implements Serializable {

    private Applicant applicant;

    public Applicant getApplicant() {
        if (applicant == null) {
            applicant = new Applicant();
        }
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    private ApplicantService applicantService;
    private VacancyService vacancyService;

    /** Creates a new instance of ApplicantMB */
    public ApplicantMB() {
        applicantService = new ApplicantService();
        vacancyService = new VacancyService();
    }

    public String add() {
        try {
            FacesMessage msg = new FacesMessage();
            msg.setSummary("");
            if (applicant.getFirstName() == null || applicant.getFirstName().equals("")) {
                msg.setSummary("You must enter first name.");
                FacesContext.getCurrentInstance().addMessage("firstName", msg);
            } else if (applicant.getLastName() == null || applicant.getLastName().equals("")) {
                msg.setSummary("You must enter last name.");
                FacesContext.getCurrentInstance().addMessage("lastName", msg);
            } else if (applicant.getEmail() == null || applicant.getEmail().equals("")) {
                msg.setSummary("You must enter email.");
                FacesContext.getCurrentInstance().addMessage("email", msg);
            } else if (applicant.getEmail() != null && applicantService.isEmailExisted(applicant.getEmail())) {
                msg.setSummary("This email is already existed.");
                FacesContext.getCurrentInstance().addMessage("email", msg);
            }
            if (!msg.getSummary().equals("")) {
                return msg.getSummary();
            }
            applicantService.beginTransaction();
            applicant = applicantService.addApplicant(applicant.getFirstName(), applicant.getLastName(), true,
                    Calendar.getInstance().getTime(), applicant.getPhoneNumber(), applicant.getEmail(),
                    applicant.getAddress(), 0.0, applicant.getLanguage(), 3, applicant.getLanguage(),
                    applicant.getSkill(), applicant.getAward(), null, null);
            applicantService.commitTransaction();
            return "addApplicant.xhtml";
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(ex.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return ex.getMessage();
        }
    }

    public String attachVacancy() {
        try {
            applicantService.beginTransaction();
            applicantService.attachVacancy(applicant.getApplicantID(), applicant.getVacancyList());
            applicantService.commitTransaction();
            return "";
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(ex.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return ex.getMessage();
        }
    }

    public List<Vacancy> getAvailaleVacancies() {
        try {
            return vacancyService.getAvailableVacancies();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "An error occured", ex.getMessage());
            facesContext.addMessage(null, message);
            return null;
        }
    }

    public int numberApplicantHired(Object obj) {
        try {
            return vacancyService.getApplicantHired(obj.toString()).size();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "An error occured", ex.getMessage());
            facesContext.addMessage(null, message);
            return -1;
        }
    }
    private List<Vacancy> listDropped;
    private List<Vacancy> listAvailable;

    public List<Vacancy> getListAvailable() {
        if (listAvailable == null && listDropped == null) {
            listAvailable = vacancyService.getAvailableVacancies();
        }
        return listAvailable;
    }

    public List<Vacancy> getListDropped() {
        if (listDropped == null) {
            listDropped = new ArrayList<Vacancy>();
        }
        return listDropped;
    }

    public void setListAvailable(List<Vacancy> listAvailable) {
        this.listAvailable = listAvailable;
    }

    public void setListDropped(List<Vacancy> listDropped) {
        this.listDropped = listDropped;
    }

    public void onVacancyDrop(DragDropEvent ddEvent) {
        Vacancy vacancy = ((Vacancy) ddEvent.getData());

        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
    }

    public void onVacancyDropReturn(DragDropEvent ddEvent) {
        Vacancy vacancy = ((Vacancy) ddEvent.getData());

        listAvailable.add(vacancy);
        listDropped.remove(vacancy);
    }
}
