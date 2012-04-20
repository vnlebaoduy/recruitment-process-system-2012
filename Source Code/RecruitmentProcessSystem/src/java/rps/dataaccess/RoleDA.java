/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Role;

/**
 *
 * @author user
 */
public class RoleDA extends AbstractDataAccess<Role>{

    public RoleDA(EntityManager em) {
        super(Role.class, em);
    }

}
