/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.entities.statistic;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class ApplicantStatistic implements Serializable {

    private String month;
    private long amount;

    public ApplicantStatistic() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
