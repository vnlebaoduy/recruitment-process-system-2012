/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rps.business;

import java.util.List;
import rps.dataaccess.VacancyDA;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class VacancyService extends AbstractService{
private VacancyDA vacancyDA;

    public VacancyService() {
        vacancyDA = new VacancyDA(getEntityManager());
    }

    public List<Vacancy> searchVacancyByTitle(String title){
        return vacancyDA.findAbsolutely("title", title);
    }
}
