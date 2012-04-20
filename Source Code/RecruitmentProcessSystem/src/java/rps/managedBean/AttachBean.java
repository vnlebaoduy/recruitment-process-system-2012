/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DragDropEvent;
import rps.business.VacancyService;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class AttachBean implements Serializable {

    private VacancyService vacancyService;

    /** Creates a new instance of AttachBean */
    public AttachBean() {
        vacancyService = new VacancyService();
    }
    // <editor-fold defaultstate="collapsed" desc="applicant.xhtml | attach-dialog.xhtml">
    // <editor-fold defaultstate="collapsed" desc="ATTACH VACANCY">

    public int numberHired(Object obj) {
        try {
            return vacancyService.getApplicantHired(obj.toString()).size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    private List<Vacancy> listDropped;
    private List<Vacancy> listAvailable;

    public void setListAvailable(List<Vacancy> listAvailable) {
        this.listAvailable = listAvailable;
    }

    public void setListDropped(List<Vacancy> listDropped) {
        this.listDropped = listDropped;
    }

    public List<Vacancy> getListAvailable() {
        try {
            if (listAvailable == null && listDropped == null) {
                listAvailable = vacancyService.getAvailableVacancies();
            }
            return listAvailable;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Vacancy>();
    }

    public List<Vacancy> getListDropped() {
        if (listDropped == null) {
            listDropped = new ArrayList<Vacancy>();
        }
        return listDropped;
    }

    public void onVacancyDrop(DragDropEvent ddEvent) {
        Vacancy vacancy = ((Vacancy) ddEvent.getData());

        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
        saveListenner();
    }

    public void attachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getDetailVacancy(obj);
        listDropped.add(vacancy);
        listAvailable.remove(vacancy);
        saveListenner();
    }

    public void detachVacancy(String obj) {
        Vacancy vacancy = vacancyService.getDetailVacancy(obj);
        listAvailable.add(vacancy);
        listDropped.remove(vacancy);
        saveListenner();
    }
    private String msgAttached;

    public String getMsgAttached() {
        return msgAttached;
    }

    public void setMsgAttached(String msgAttached) {
        this.msgAttached = msgAttached;
    }

    public void saveListenner() {
        Map<String, Object> params =
                FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        ApplicantBean bean = (ApplicantBean) params.get("applicantBean");
        if (bean != null) {
            bean.setListVacancies(listDropped);
            int size = listDropped.size();
            if (size > 0) {
                if (size == 1) {
                    msgAttached = "   ( 1 vacancy attached for new applicant )";
                } else {
                    msgAttached = "   ( " + size + " vacancies attached for new applicant )";
                }
            } else {
                msgAttached = "";
            }
        }
    }
    // </editor-fold>
    // </editor-fold>
}
