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
import rps.business.EmployeeService;
import rps.entities.Employee;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@FacesConverter("employeeConverter")
public class EmployeeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            EmployeeService service = new EmployeeService();
            Employee employee = service.getEmployeeInfo(value);
            if (employee == null) {
                FacesMessage msg = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Employee is not found",
                        "Employee is not found");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return employee;
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Employee is not found",
                    "Employee is not found");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Employee employee = (Employee) value;
            return employee.getEmployeeID();
        }
        return null;
    }
}
