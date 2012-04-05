/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import rps.dataaccess.EmployeeDA;
import rps.entities.Employee;

/**
 *
 * @author user
 */
public class EmployeeService extends AbstractService {

    private EmployeeDA employeeDA;

    public EmployeeService() {
        employeeDA = new EmployeeDA(getEntityManager());
    }

    public Employee getEmployeeInfo(String id){
        return employeeDA.find(id);
    }
}
