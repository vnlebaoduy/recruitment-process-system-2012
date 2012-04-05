/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.Applicant;

/**
 *
 * @author user
 */
public class ApplicantDA extends AbstractDataAccess<Applicant> {

    public ApplicantDA(EntityManager em) {
        super(Applicant.class, em);
    }
}
