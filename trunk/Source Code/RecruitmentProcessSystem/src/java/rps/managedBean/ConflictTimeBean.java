/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.managedBean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import rps.entities.Interview;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ConflictTimeBean implements Serializable{

    /** Creates a new instance of ConflictTimeBean */
    public ConflictTimeBean() {
    }
    // <editor-fold defaultstate="collapsed" desc="conflict-time-dialog">
    // <editor-fold defaultstate="collapsed" desc="DISPLAY CONFLICT">
    private Interview interview;

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    private String msgConflict;

    public String getMsgConflict() {
        return msgConflict;
    }

    public void setMsgConflict(String msgConflict) {
        this.msgConflict = msgConflict;
    }

    private List<Interview> listConflict;

    public List<Interview> getListConflict() {
        return listConflict;
    }

    public void setListConflict(List<Interview> listConflict) {
        this.listConflict = listConflict;
    }
    
    // </editor-fold>
    // </editor-fold>
}
