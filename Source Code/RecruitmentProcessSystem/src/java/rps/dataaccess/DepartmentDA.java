/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Department;

/**
 *
 * @author user
 */
public class DepartmentDA extends AbstractDataAccess<Department>{

    public DepartmentDA(EntityManager em) {
        super(Department.class, em);
    }

}
