/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CloseEvent;
import rps.business.AccountService;
import rps.entities.Account;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class AccountMB {

    private Account account;
    private String password;
    private String confirm;

    public Account getAccount() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private AccountService accountService;

    /** Creates a new instance of AccountMB */
    public AccountMB() {
        accountService = new AccountService();
    }

    public String validate() {
        String redirect = "";
        Account acc = accountService.getAccount(account.getUserName(), account.getPassword());
        if (acc != null) {
            this.setAccount(acc);
            redirect = "info.xhtml?faces-redirect=true";
        } else {
            FacesMessage msg = new FacesMessage("Username or password is incorrect.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            redirect = "login.xhtml";
        }
        return redirect;
    }

    public String changePass() {
        try {
            accountService.beginTransaction();
            this.setAccount(accountService.updateAccount(account.getUserName(), password));
            accountService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Form closed", "Your password has been changed");

            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error occured", ex.getMessage());

            facesContext.addMessage(null, message);
        }
        return "info.xhtml";
    }

    public void handleClose(CloseEvent event) {
        if (!account.getIsChangedPassword()) {
            accountService.beginTransaction();
            this.setAccount(accountService.updateAccount(account.getUserName(), true));
            accountService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Form closed", "Your password has not been changed");

            facesContext.addMessage(null, message);
        }
    }

    public void validatePassword() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        facesContext.addMessage("confirmPassword", message);

        
    }
}
