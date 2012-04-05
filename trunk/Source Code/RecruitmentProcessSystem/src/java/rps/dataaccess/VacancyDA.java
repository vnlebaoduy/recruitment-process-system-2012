/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class VacancyDA extends AbstractDataAccess<Vacancy> {

    public VacancyDA(EntityManager em) {
        super(Vacancy.class, em);
    }
}
