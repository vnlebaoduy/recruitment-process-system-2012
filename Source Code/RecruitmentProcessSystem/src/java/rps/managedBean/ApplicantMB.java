/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import rps.business.ApplicantService;
import rps.business.VacancyService;
import rps.entities.Applicant;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ApplicantMB implements Serializable {

    private Applicant applicant;
    private List<Applicant> lstApplicant;
    private String applicantID;

    public Applicant getApplicant() {
        if (applicant == null) {
            applicant = new Applicant();
            applicant.setGender(true);
        }
        return applicant;
    }

    public List<Applicant> getLstApplicant() {
        return lstApplicant;
    }

    public void setLstApplicant(List<Applicant> lstApplicant) {
        this.lstApplicant = lstApplicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }
    private ApplicantService applicantService;
    private VacancyService vacancyService;

    /** Creates a new instance of ApplicantMB */
    public ApplicantMB() {
        applicantService = new ApplicantService();
        vacancyService = new VacancyService();
    }

    public String search() {
        applicantService = new ApplicantService();
        lstApplicant = applicantService.getListApplicantByID(applicantID);
        return null;
    }

    public String add() {
        try {
//            FacesMessage msg = new FacesMessage();
//            msg.setSummary("");
//            if (applicant.getEmail() != null) {
//                if (!applicant.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
//                    msg.setSummary("Email is wrong format.");
//                    FacesContext.getCurrentInstance().addMessage("email", msg);
//                } else if (applicantService.isEmailExisted(applicant.getEmail())) {
//                    msg.setSummary("This email is already existed.");
//                    FacesContext.getCurrentInstance().addMessage("email", msg);
//                }
//            }
//            if (!msg.getSummary().equals("")) {
//                return null;
//            }
            if (applicant.getSalaryRequirement() == null) {
                applicant.setSalaryRequirement(0.0);
            }
            if (applicant.getYearOfExperience() == null) {
                applicant.setYearOfExperience(0);
            }
            applicantService.beginTransaction();
            applicant = applicantService.addApplicant(applicant.getFirstName(), applicant.getLastName(),
                    applicant.getGender(), applicant.getDob(), applicant.getPhoneNumber(),
                    applicant.getEmail(), applicant.getAddress(), applicant.getSalaryRequirement(),
                    applicant.getLanguage(), applicant.getYearOfExperience(), applicant.getLanguage(),
                    applicant.getSkill(), applicant.getAward(), applicant.getVacancyList(), null);
            applicantService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Create new successfull", "Click <a href='applicants.xhtml'>here</a> to view all applicants");
            facesContext.addMessage(null, message);
            applicant = null;
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "An error occured", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return null;
    }

//    public String attachVacancy() {
//        try {
//            applicantService.beginTransaction();
//            applicantService.attachVacancy(applicant.getApplicantID(), applicant.getVacancyList());
//            applicantService.commitTransaction();
//        } catch (Exception ex) {
//            FacesMessage msg = new FacesMessage(ex.getMessage());
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//        return null;
//    }
    public List<Vacancy> getAvailaleVacancies() {
        try {
            return vacancyService.getAvailableVacancies();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
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
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "An error occured", ex.getMessage());
            facesContext.addMessage(null, message);
            return -1;
        }
    }

    public void validateEmail(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isRequired() && uiInput.isValid()) {
            String emailValue = (String) value;
            if (!emailValue.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Email is invalid",
                        "Email is invalid");
                throw new ValidatorException(message);
            } else if (applicantService.isEmailExisted(emailValue)) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "This email is already existed",
                        "This email is already existed");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateYear(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isValid()) {
            int numberValue = (Integer) value;
            if (numberValue > 50) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Years of experience is invalid",
                        "Years of experience is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateSalary(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isValid()) {
            double numberValue = (Double) value;
            if (numberValue > 1000000000) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Salary requirements is too much",
                        "Salary requirements is too much");
                throw new ValidatorException(message);
            }
        }
    }

    public void validatePhoneNumber(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isValid()) {
            String phoneValue = (String) value;
            if (!phoneValue.isEmpty()) {
                if (!phoneValue.matches("\\d{10}")) {
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Phone number is invalid",
                            "Phone number is invalid");
                    throw new ValidatorException(message);
                }
            }
        }
    }

    public void validateDOB(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isValid()) {
            Date dobValue = (Date) value;
            if (dobValue.compareTo(Calendar.getInstance().getTime()) > 0) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Date is invalid",
                        "Date is invalid");
                throw new ValidatorException(message);
            }
        }
    }

    public String getCurrentDate() {
        Format format = new SimpleDateFormat("MM/dd/yy");
        return format.format(Calendar.getInstance().getTime());
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

    public void onVacancyDrop(DragDropEvent ddEvent) {
        Vacancy vacancy = ((Vacancy) ddEvent.getData());

        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
    }

    public String attachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getVacancyByID(obj);

        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
        return null;
    }

    public String detachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getVacancyByID(obj);

        listAvailable.add(vacancy);
        listDropped.remove(vacancy);
        return null;
    }
    private String numberVacancies;

    public String getNumberVacancies() {
        return numberVacancies;
    }

    public String saveChanges() {
        applicant.setVacancyList(listDropped);
        int size = applicant.getVacancyList().size();
        if (size > 0) {
            if (size == 1) {
                numberVacancies = "1 vacancy attached for new applicant";
            } else {
                numberVacancies = size + " vacancies attached for new applicant";
            }
        }
        return null;
    }

    public void saveListenner() {
        this.getApplicant().setVacancyList(listDropped);
        int size = this.getApplicant().getVacancyList().size();
        if (size > 0) {
            if (size == 1) {
                numberVacancies = "1 vacancy attached for new applicant";
            } else {
                numberVacancies = size + " vacancies attached for new applicant";
            }
        }
    }
}
