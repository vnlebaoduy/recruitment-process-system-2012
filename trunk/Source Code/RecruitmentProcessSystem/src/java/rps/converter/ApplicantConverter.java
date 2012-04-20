/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import rps.business.ApplicantService;
import rps.entities.Applicant;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@FacesConverter("applicantConverter")
public class ApplicantConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            ApplicantService service = new ApplicantService();
            Applicant applicant = service.getApplicant(value);
            if (applicant == null) {
                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Applicant is not found",
                        "Applicant is not found");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return applicant;
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Applicant is not found",
                    "Applicant is not found");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Applicant applicant = (Applicant) value;
            return applicant.getApplicantID();
        }
        return null;
    }
}
