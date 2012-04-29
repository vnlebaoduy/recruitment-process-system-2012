/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CloseEvent;
import rps.business.AccountService;
import rps.business.SystemSettings;
import rps.entities.Account;
import rps.utility.MD5;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class AccountMB implements Serializable {

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
        this.confirm = confirm.replaceAll(" ", "");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.replaceAll(" ", "");
    }
    private AccountService accountService;

    /** Creates a new instance of AccountMB */
    public AccountMB() {
        accountService = new AccountService();
    }

    public String validate() {
        String redirect = "";
        String strMD5 = MD5.getMD5(account.getPassword());
        Account acc = accountService.getAccount(account.getUserName(), strMD5);
        if (acc != null) {
            this.setAccount(acc);
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();
            if (session.getAttribute("account") == null) {
                session.setAttribute("account", acc);
            }
            SystemSettings settings = new SystemSettings();
            settings.InitMenuBar(account);
            settings.InitSideBar(account);
            if (session.getAttribute("setting") == null) {
                session.setAttribute("setting", settings);
            }
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
            String strMD5 = MD5.getMD5(password);
            accountService.beginTransaction();
            this.setAccount(accountService.updateAccount(account.getUserName(), strMD5));
            this.setAccount(accountService.updateAccount(account.getUserName(), true));
            accountService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "INFORMATION", "Your password has been changed");
            facesContext.addMessage(null, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void handleClose(CloseEvent event) {
        if (!account.getIsChangedPassword()) {
            accountService.beginTransaction();
            this.setAccount(accountService.updateAccount(account.getUserName(), true));
            accountService.commitTransaction();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "WARNING", "Your password has not been changed");
            facesContext.addMessage(null, message);
        }
    }

    public void validatePassword(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {

        UIInput uiInput = (UIInput) componentToValidate;
        if (uiInput.isValid()) {
            String str = (String) value;
            if (str.length() < 6) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Password must have at least 6 characters",
                        "Password must have at least 6 characters");
                throw new ValidatorException(message);
            }
            if (account.getPassword().equalsIgnoreCase(str)) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "New password must be different with old password",
                        "New password must be different with old password");
                throw new ValidatorException(message);
            }
        }
    }

    public void validateConfirmPassword(
            FacesContext context,
            UIComponent componentToValidate,
            Object value)
            throws ValidatorException {
        UIInput passwordComponent = (UIInput) componentToValidate.getAttributes().get("passwordComponent");
        if (passwordComponent.isRequired() && passwordComponent.isValid()) {
            String passwordValue = (String) passwordComponent.getValue();
            String confirmPasswordValue = (String) value;
            if (!passwordValue.equals(confirmPasswordValue)) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Confirm password is not match",
                        "Confirm password is not match");
                throw new ValidatorException(message);
            }
        }
    }
    private boolean hRGroup;
    private boolean admin;
    private boolean interviewer;

    public boolean isAdmin() {
        admin = accountService.isAdmin(account.getUserName());
        return admin;
    }

    public boolean ishRGroup() {
        hRGroup = accountService.isHRGroup(account.getUserName());
        return hRGroup;
    }

    public boolean isInterviewer() {
        interviewer = accountService.isInterviewer(account.getUserName());
        return interviewer;
    }

    public String logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("account") != null) {
            session.removeAttribute("account");
        }
        if (session.getAttribute("setting") != null) {
            session.removeAttribute("setting");
        }
        account = null;
        password = "";
        confirm = "";
        return "login.xhtml?faces-redirect=true";
    }
}
