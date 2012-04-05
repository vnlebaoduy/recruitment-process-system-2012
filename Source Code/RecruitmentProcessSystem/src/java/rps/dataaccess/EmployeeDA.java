/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Employee;

/**
 *
 * @author user
 */
public class EmployeeDA extends AbstractDataAccess<Employee>{

    public EmployeeDA(EntityManager em) {
        super(Employee.class, em);
    }

}
