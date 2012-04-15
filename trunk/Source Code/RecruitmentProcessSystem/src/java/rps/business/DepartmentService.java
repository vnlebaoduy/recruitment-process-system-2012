/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.business;

import java.util.List;
import rps.dataaccess.DepartmentDA;
import rps.entities.Department;

/**
 *
 * @author tung
 */
public class DepartmentService extends AbstractService {
    private DepartmentDA departmentDA;

    public DepartmentService() {
        departmentDA = new DepartmentDA(getEntityManager());
    }

    public Department getDeparment(String nameDepartment){
        List<Department> list= departmentDA.findAbsolutely("name", nameDepartment);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public List<Department> getDepartments(){
        return departmentDA.findAll();
    }

    public Department getDeparmentById(String id){
        List<Department> list= departmentDA.findAbsolutely("departmentID", id);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
