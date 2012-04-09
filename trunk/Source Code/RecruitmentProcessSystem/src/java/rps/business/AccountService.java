/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.business;

import rps.dataaccess.AccountDA;
import rps.dataaccess.FindResult;
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
    
    public Account updateAccount(String userName, boolean isChanged){
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

}
