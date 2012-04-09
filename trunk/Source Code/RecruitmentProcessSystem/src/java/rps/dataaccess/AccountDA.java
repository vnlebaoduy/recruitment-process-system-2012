/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Account;

/**
 *
 * @author user
 */
public class AccountDA extends AbstractDataAccess<Account> {

    public AccountDA(EntityManager em) {
        super(Account.class, em);
    }
}
