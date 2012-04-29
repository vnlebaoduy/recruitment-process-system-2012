/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import rps.business.ApplicantService;
import rps.entities.Applicant;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ApplicantBean implements Serializable {

    private ApplicantService applicantService;

    /** Creates a new instance of ApplicantBean */
    public ApplicantBean() {
        applicantService = new ApplicantService();
    }
    // <editor-fold defaultstate="collapsed" desc="applicant.xhtml | attach-dialog.xhtml">
    // <editor-fold defaultstate="collapsed" desc="ADD APPLICANT">
    private Applicant applicant;

    public Applicant getApplicant() {
        if (applicant == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String paramID = request.getParameter("id");
            if (paramID == null || paramID.equals("")) {
                applicant = new Applicant();
            } else {
                try {
                    applicant = applicantService.getApplicant(paramID);
                } catch (Exception ex) {
                    FacesMessage message = new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "WARNING",
                            "Applicant not found!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    applicant = new Applicant();
                }
            }
        }
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void validateEmail(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isRequired() && uiInput.isValid()) {
            String emailValue = (String) value;
            if (applicant.getEmail() != null && !applicant.getEmail().equals("")) {
                if (!applicant.getEmail().equals(emailValue)) {
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
    
    private List<Vacancy> listVacancies;

    public void setListVacancies(List<Vacancy> listVacancies) {
        this.listVacancies = listVacancies;
    }

    public void add() {
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
                    applicant.getLanguage(), applicant.getYearOfExperience(), applicant.getDegree(),
                    applicant.getSkill(), applicant.getAward(), null);
            applicantService.commitTransaction();
            if (!listVacancies.isEmpty()) {
                attach();
            }
            resetAttachForm();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION",
                    "Create an new applicant successful");
            facesContext.addMessage(null, message);
            applicant = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void attach() {
        try {
            List<Interview> list = applicantService.attachVacancies(applicant, listVacancies);
            applicantService.beginTransaction();
            applicant = applicantService.updateApplicant(applicant.getApplicantID(),
                    applicant.getFirstName(), applicant.getLastName(),
                    applicant.getGender(), applicant.getDob(), applicant.getPhoneNumber(),
                    applicant.getEmail(), applicant.getAddress(),
                    applicant.getSalaryRequirement(), applicant.getLanguage(),
                    applicant.getYearOfExperience(), applicant.getDegree(),
                    applicant.getSkill(), applicant.getAward(), list, applicant.getCreatedDate(), 99);
            applicantService.commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void resetAttachForm() {
        Map<String, Object> params =
                FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        AttachBean bean = (AttachBean) params.get("attachBean");
        if (bean != null) {
            bean.setListAvailable(null);
            bean.setListDropped(null);
            bean.setMsgAttached("");
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="EDIT APPLICANT">

    public String edit() {
        try {
            if (applicant.getSalaryRequirement() == null) {
                applicant.setSalaryRequirement(0.0);
            }
            if (applicant.getYearOfExperience() == null) {
                applicant.setYearOfExperience(0);
            }
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id = params.get("id");
            applicantService.beginTransaction();
            applicant = applicantService.updateApplicant(id, applicant.getFirstName(),
                    applicant.getLastName(), applicant.getGender(),
                    applicant.getDob(), applicant.getPhoneNumber(),
                    applicant.getEmail(), applicant.getAddress(),
                    applicant.getSalaryRequirement(), applicant.getLanguage(),
                    applicant.getYearOfExperience(), applicant.getLanguage(),
                    applicant.getSkill(), applicant.getAward(), applicant.getInterviewList(),
                    applicant.getCreatedDate(), applicant.getStatus());
            applicantService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION",
                    "All of informations have been saved");
            facesContext.addMessage(null, message);
            applicant = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "applicants.html?faces-redirect=true";
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="progress-interview-dialog.xhtml">
    // <editor-fold defaultstate="collapsed" desc="DISPLAY APPLICANT">
    public String gender(Object obj) {
        Applicant app = (Applicant) obj;
        if (app != null && app.getGender()) {
            return "Male";
        }
        return "Female";
    }
    // </editor-fold>
    // </editor-fold>
}
