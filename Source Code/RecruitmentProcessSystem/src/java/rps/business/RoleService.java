/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.List;
import rps.dataaccess.RoleDA;
import rps.entities.Account;
import rps.entities.Role;

/**
 *
 * @author user
 */
public class RoleService extends AbstractService {

    private RoleDA roleDA;

    public RoleService() {
        roleDA = new RoleDA(getEntityManager());
    }

    public List<Account> getAccounts(String roleName){
        return roleDA.find(roleName).getAccountList();
    }

    public Role getRole(String roleName){
        return roleDA.find(roleName);
    }
}
