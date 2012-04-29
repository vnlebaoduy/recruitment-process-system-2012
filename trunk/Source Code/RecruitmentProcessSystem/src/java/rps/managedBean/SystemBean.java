/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import rps.business.Page;
import rps.business.PageGroup;
import rps.business.SystemSettings;
import rps.entities.Account;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class SystemBean implements Serializable{

    private SystemSettings systemSettings;

    public SystemSettings getSystemSettings() {
        if(systemSettings==null){
            HttpServletRequest request = (HttpServletRequest)
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
            systemSettings = (SystemSettings)request.getSession().getAttribute("setting");
        }
        return systemSettings;
    }

    /** Creates a new instance of SystemBean */
    public SystemBean() {
        styleCSS();
    }

    private void styleCSS() {
        HttpServletRequest request = (HttpServletRequest)
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        String pageName = url.substring(url.lastIndexOf("/")+1);
        for (PageGroup pageGroup : getSystemSettings().getListPages()) {
            boolean currentPageGroup = false;
            for (Page page : pageGroup.getPages()) {
                if (page.getUrl().equalsIgnoreCase(pageName)) {
                    page.setCurrent(true);
                    currentPageGroup = true;
                } else {
                    page.setCurrent(false);
                }
            }
            pageGroup.getPage().setCurrent(currentPageGroup);
        }
    }
}
