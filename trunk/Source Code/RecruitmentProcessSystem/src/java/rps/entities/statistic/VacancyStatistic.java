/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.entities.statistic;

import java.io.Serializable;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class VacancyStatistic implements Serializable{
    private Vacancy vacancy;
    private long numberApplicants;

    public VacancyStatistic() {
    }
    
    public long getNumberApplicants() {
        return numberApplicants;
    }

    public void setNumberApplicants(long numberApplicants) {
        this.numberApplicants = numberApplicants;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}
