/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import rps.dataaccess.AccountDA;
import rps.entities.Account;

/**
 *
 * @author user
 */
public class AccountService extends AbstractService {

    private AccountDA accountDA;

    public AccountService() {
        accountDA = new AccountDA(getEntityManager());
    }

    public boolean validate(String userName, String password) {
        Account account = accountDA.find(userName);
        if (account == null) {
            return false;
        }
        if (!account.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    public void changePassword(String userName, String oldPassword, String newPassword) {
        if(validate(userName, newPassword)){
            Account account = accountDA.find(userName);
            account.setPassword(newPassword);
            account.setIsChangedPassword(Boolean.TRUE);
            accountDA.edit(account);
        }
    }

    public boolean isChangePassword(String userName){
        return accountDA.find(userName).getIsChangedPassword();
    }
}
