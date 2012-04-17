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
import rps.business.VacancyService;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@FacesConverter("vacancyConverter")
public class VacancyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            VacancyService service = new VacancyService();
            Vacancy vacancy = service.getDetailVacancy(value);
            if (vacancy == null) {
                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Vacancy is not found",
                        "Vacancy is not found");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return vacancy;
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Vacancy is not found",
                    "Vacancy is not found");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Vacancy vacancy = (Vacancy)value;
            return vacancy.getVacancyID();
        }
        return null;
    }
}