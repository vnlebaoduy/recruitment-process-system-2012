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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;
import rps.entities.Vacancy;

/**
 *
 * @author Bach Luong
 */
public class InterviewDA extends AbstractDataAccess<Interview> {

    public InterviewDA(EntityManager em) {
        super(Interview.class, em);
    }

    public FindResult<Interview> searchInterview(Date startedTime, Date endedTime) {

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

        predicates.add(cb.between(root.get("startedTime"),
                startedTime, endedTime));

        predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        cq.select(root).where(predicate).orderBy(cb.asc(root.get("startedTime")));
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }

    public FindResult<Interview> searchInterview(Employee employee, Applicant applicant,
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
        predicate = null;
        if (employee != null) {
            predicate = cb.equal(root.get("employee"), employee);
            if (applicant != null) {
                predicate = cb.and(predicate, cb.equal(root.get("applicant"), applicant));
            }
        } else if (applicant != null) {
            predicate = cb.equal(root.get("applicant"), applicant);
        }

        //start ---- startedTime ---- end
        Predicate predicate1 = cb.greaterThan(root.get("startedTime"),
                startedTime);

        predicate1 = cb.and(predicate1, cb.lessThan(root.get("startedTime"),
                endedTime));

        //start ---- endedTime ---- end
        Predicate predicate2 = cb.greaterThan(root.get("endedTime"),
                startedTime);

        predicate2 = cb.and(predicate2, cb.lessThan(root.get("endedTime"),
                endedTime));

        //startTime ---- start ---- end --- endedTime
        Predicate predicate3 = cb.lessThanOrEqualTo(root.get("startedTime"),
                startedTime);

        predicate3 = cb.and(predicate3, cb.greaterThanOrEqualTo(root.get("endedTime"),
                endedTime));

        //start ---- startTime --- endedTime ---- end
        Predicate predicate4 = cb.greaterThanOrEqualTo(root.get("startedTime"),
                startedTime);

        predicate4 = cb.and(predicate4, cb.lessThanOrEqualTo(root.get("endedTime"),
                endedTime));

        predicate = cb.and(predicate, cb.or(predicate1, predicate2, predicate3, predicate4));

        cq.select(root).where(predicate);
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }
//
//    public FindResult<Interview> searchInterviewAbsolutely(String id, Employee employee,
//            Applicant applicant, Vacancy vacancy, int status, int aVStatus,
//            Date startedTime, Date endedTime) {
//
//        FindResult<Interview> results;
//        CriteriaBuilder cb;
//        CriteriaQuery<Interview> cq;
//        CriteriaQuery<Long> countCq;
//        Root root;
//        Predicate predicate;
//
//        //Create CriteraBuilder
//        cb = getEntityManager().getCriteriaBuilder();
//        //Create CriteraQuery
//        cq = cb.createQuery(Interview.class);
//        countCq = cb.createQuery(Long.class);
//        //Create results
//        results = new FindResult<Interview>();
//        //Create root
//        root = cq.from(Interview.class);
//
//        //Create Predicate
//        List<Predicate> predicates = new ArrayList<Predicate>();
//
//        if (id != null) {
//            predicates.add(cb.equal(root.get("interviewID"), id));
//        }
//        if(employee!=null){
//            predicates.add(cb.equal(root.get("employee"), employee));
//        }
//        if(applicant!=null){
//            predicates.add(cb.equal(root.get("applicant"), applicant));
//        }
//        if(status!=-1){
//            predicates.add(cb.equal(root.get("status"), status));
//        }
//        if(aVStatus!=-1){
//            predicates.add(cb.equal(root.get("aVStatus"), aVStatus));
//        }
//        if(startedTime!=null){
//
//        }
//        //start ---- startedTime ---- end
//        Predicate predicate1 = cb.between(root.get("startedTime"),
//                startedTime, endedTime);
//
//        //start ---- endedTime ---- end
//        Predicate predicate2 = cb.between(root.get("endedTime"),
//                startedTime, endedTime);
//
//        //startTime ---- start ---- end --- endedTime
//        Predicate predicate3 = cb.lessThan(root.get("startedTime"),
//                startedTime);
//
//        predicate3 = cb.and(predicate3, cb.greaterThan(root.get("endedTime"),
//                endedTime));
//
//        //start ---- startTime --- endedTime ---- end
//        Predicate predicate4 = cb.greaterThan(root.get("startedTime"),
//                startedTime);
//
//        predicate4 = cb.and(predicate4, cb.lessThan(root.get("endedTime"),
//                endedTime));
//
//        predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
//        cq.select(root).where(predicate);
//        countCq.select(cb.count(root)).where(predicate);
//
//        Query q = getEntityManager().createQuery(cq);
//        Query countQ = getEntityManager().createQuery(countCq);
//
//        int count = ((Long) countQ.getSingleResult()).intValue();
//
//        results.addAll(q.getResultList());
//        results.setCount(count);
//
//        return results;
//    }

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
