/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import rps.business.AccountService;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class AccountMB {

    private String userName;
    private String password;
    private String newPass;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    private AccountService accountService;

    /** Creates a new instance of AccountMB */
    public AccountMB() {
        accountService = new AccountService();
    }

    public String validate() {
        String redirect = "";
        if (accountService.validate(userName, password)) {
            redirect = "info.xhtml?faces-redirect=true";
            if (accountService.isChangePassword(userName)) {
                redirect = "info.xhtml#message";
            }
        } else {
            //throw new ValidatorException(new FacesMessage("Username or password is incorrect."));
            redirect = "login.xhtml";
        }
        return redirect;
    }

    public String changePass() {
        accountService.changePassword(userName, password, newPass);
        return "info.xhtml";
    }
}
