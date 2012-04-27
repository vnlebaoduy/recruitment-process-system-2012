/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import java.util.List;
import rps.dataaccess.AccountDA;
import rps.dataaccess.FindResult;
import rps.entities.Account;
import rps.entities.Role;

/**
 *
 * @author user
 */
public class AccountService extends AbstractService {

    private AccountDA accountDA;

    public AccountService() {
        accountDA = new AccountDA(getEntityManager());
    }

    public Account getAccount(String userName, String password) {
        FindResult<Account> result = accountDA.findAbsolutely(new String[]{"userName", "password"}, new String[]{userName, password}, null, null, -1, -1);
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public Account updateAccount(String userName, String password) {
        Account account = accountDA.find(userName);
        if (account != null) {
            account.setPassword(password);
            account.setIsChangedPassword(Boolean.TRUE);
            accountDA.edit(account);
        }
        return account;
    }

    public Account updateAccount(String userName, boolean isChanged) {
        Account account = accountDA.find(userName);
        if (account != null) {
            account.setIsChangedPassword(isChanged);
            accountDA.edit(account);
        }
        return account;
    }

    public boolean isChangePassword(String userName) {
        return accountDA.find(userName).getIsChangedPassword();
    }

    public boolean isAdmin(String userName) {
        return checkRole(userName, "Administrator");
    }

    public boolean isHRGroup(String userName) {
        return checkRole(userName, "HR Group");
    }

    public boolean isInterviewer(String userName) {
        return checkRole(userName, "Interviewer");
    }

    private boolean checkRole(String userName, String roleName) {
        List<Role> roles = accountDA.find(userName).getRoleList();
        for (Role role : roles) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }
}
