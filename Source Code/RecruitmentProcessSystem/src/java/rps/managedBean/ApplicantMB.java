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
     private List<Applicant> lstNewApplicant;

    public List<Applicant> getLstNewApplicant() {
         applicantService = new ApplicantService();
        List<Applicant> lstAllApplicant = applicantService.getListApplicant();
        Applicant tempObj;
        for (int x = 0; x < lstAllApplicant.size() - 1; x++) {
            for (int y = x + 1; y < lstAllApplicant.size(); y++) {
                if (lstAllApplicant.get(x).getCreatedDate().before(lstAllApplicant.get(y).getCreatedDate())) {
                    tempObj = lstAllApplicant.get(x);
                    lstAllApplicant.set(x, lstAllApplicant.get(y));
                    lstAllApplicant.set(y, tempObj);
                }
            }
        }
        return lstAllApplicant.subList(lstAllApplicant.size() - 4, lstAllApplicant.size() - 1);
        
    }

    public void setLstNewApplicant(List<Applicant> lstNewApplicant) {
        this.lstNewApplicant = lstNewApplicant;
    }
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

    public String addNew() {
        return "applicant.xhtml";
    }

    public String editAppl(Object appl){
    String applID = (String) appl;
    return "applicant.xhtml?faces-redirect=true&ID="+applID;
    }
    public String add() {
        try {
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
            listAvailable = null;
            listDropped = null;
            numberVacancies = "";
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "An error occured", ex.getMessage());
            facesContext.addMessage(null, message);
        }
        return null;
    }

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
        saveListenner();
    }
/*
    public void attachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getDetailVacancy(obj);
        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
        saveListenner();
    }

    public void detachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getDetailVacancy(obj);
        listAvailable.add(vacancy);
        listDropped.remove(vacancy);
        saveListenner();
    }
 * 
 */
    private String numberVacancies;

    public String getNumberVacancies() {
        return numberVacancies;
    }

    public void saveListenner() {
        this.getApplicant().setVacancyList(listDropped);
        int size = this.getApplicant().getVacancyList().size();
        if (size > 0) {
            if (size == 1) {
                numberVacancies = "   ( 1 vacancy attached for new applicant )";
            } else {
                numberVacancies = "   ( " + size + " vacancies attached for new applicant )";
            }
        }
    }

    public String statusValue(Object obj) {
        try {
            int status = (Integer) obj;
            String value = "";
            switch (status) {
                case 0:
                    value = "Not in progress";
                    break;
                case 99:
                    value = "In progress";
                    break;
                case 1:
                    value = "Hired";
                    break;
                case -1:
                    value = "Banded";
                    break;
                default:
                    break;
            }
            return value;
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "An error occured",
                    ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            ex.printStackTrace();
            return null;
        }
    }

    public String gender(Object obj) {
        Applicant app = (Applicant) obj;
        if (app != null && app.getGender()) {
            return "Male";
        }
        return "Female";
    }
}
