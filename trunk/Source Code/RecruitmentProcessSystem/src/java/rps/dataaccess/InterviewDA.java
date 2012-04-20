/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;

/**
 *
 * @author Bach Luong
 */
public class InterviewDA extends AbstractDataAccess<Interview> {

    public InterviewDA(EntityManager em) {
        super(Interview.class, em);
    }

    public FindResult<Interview> searchInterview(Employee employee,
            Applicant applicant, Date startedTime, Date endedTime, Integer[] status,
            int fromIndex, int toIndex) {
        return null;
    }

    public FindResult<Interview> searchInterview(Employee employee,
            Date startedTime, Date endedTime) {

        FindResult<Interview> results;
        CriteriaBuilder cb;
        CriteriaQuery<Interview> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Interview.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Interview>();
        //Create root
        root = cq.from(Interview.class);

        //Create Predicate
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (employee != null) {
            predicates.add(cb.equal(root.get("employee"),employee));
        }
        predicates.add(cb.between(root.get("startedTime"),
                startedTime, endedTime));

        predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        cq.select(root).where(predicate);
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }

    public FindResult<Interview> getNotRemoveInterview(Employee employee, int status) {

        FindResult<Interview> results;
        CriteriaBuilder cb;
        CriteriaQuery<Interview> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Interview.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Interview>();
        //Create root
        root = cq.from(Interview.class);

        //Create Predicate
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("employee"), employee));
        predicates.add(
                cb.notEqual(root.get("status"), status));

        predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        cq.select(root).where(predicate);
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }
}
