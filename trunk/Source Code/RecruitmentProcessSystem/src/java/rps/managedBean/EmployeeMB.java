/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import rps.business.EmployeeService;
import rps.entities.Employee;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class EmployeeMB implements Serializable{

    private Employee employee;

    public Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    private EmployeeService employeeService;

    public EmployeeMB() {
        employeeService = new EmployeeService();
    }

    public String gender(Object obj) {
        Employee em = (Employee) obj;
        if (em != null && em.getGender()) {
            return "Male";
        }
        return "Female";
    }
}
