/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import javax.persistence.EntityManager;
import rps.entities.SchedulingInterview;

/**
 *
 * @author Bach Luong
 */
public class SchedulingInterviewDA extends AbstractDataAccess<SchedulingInterview> {

    public SchedulingInterviewDA(EntityManager em) {
        super(SchedulingInterview.class, em);
    }
}
