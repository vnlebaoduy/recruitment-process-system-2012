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
import rps.entities.Schedule;

/**
 *
 * @author Bach Luong
 */
public class ScheduleDA extends AbstractDataAccess<Schedule> {

    public ScheduleDA(EntityManager em) {
        super(Schedule.class, em);
    }

    public FindResult<Schedule> searchSchedule(Employee employee,
            Applicant applicant, Date startedTime, Date endedTime, Integer[] status,
            int fromIndex, int toIndex) {

        FindResult<Schedule> results;
        CriteriaBuilder cb;
        CriteriaQuery<Schedule> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Schedule.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Schedule>();
        results.setFromIndex(fromIndex);
        results.setToIndex(toIndex);
        //Create root
        root = cq.from(Schedule.class);

        //Create Predicate
        List<Predicate> predicates = new ArrayList<Predicate>();


//        predicates.add(cb.and(
//                cb.equal(root.get("album").get("security"), new Security("Friend")),
//                cb.equal(root.get("album").get("account"), currentUser)));


//        AccountDA accountDA = new AccountDA(getEntityManager());
//        FindResult<Account> friends = accountDA.getFriends(currentUser, -1, -1);
//        if (friends.size() > 0) {
//            List<String> friendsId = new ArrayList<String>();
//            if (friends.size() > 0) {
//                for (Account friend : friends) {
//                    friendsId.add(friend.getAccountId());
//                }
//            }
//            predicates.add(cb.and(
//                    cb.equal(root.get("album").get("security"), new Security("Friend")),
//                    root.get("album").get("account").get("accountId").in(friendsId)));
//        }
//
//        predicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
//
//        predicate = cb.and(predicate, cb.like(root.get("title"), "%" + keyWord + "%"));

//        cq.select(root).where(predicate);
//        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        if (fromIndex > -1) {
            q.setFirstResult(fromIndex);
        } else {
            q.setFirstResult(0);
        }
        if (toIndex > -1) {
            q.setMaxResults(toIndex - fromIndex);
        }
        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }

    public FindResult<Schedule> searchSchedule(Date date) {

        FindResult<Schedule> results;
        CriteriaBuilder cb;
        CriteriaQuery<Schedule> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Schedule.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Schedule>();
        //Create root
        root = cq.from(Schedule.class);

        //Create Predicate
        List<Predicate> predicates = new ArrayList<Predicate>();

        long interval = 24 * 1000 * 60 * 60; // Time 1 day - miliseconds
        Date start = date;
        Date end = new Date(date.getTime() + interval);
        predicates.add(
                cb.between(root.get("startedTime"),
                start, end));

        predicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        cq.select(root).where(predicate);
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }

    public FindResult<Schedule> getNotRemoveSchedule(Employee employee, int status) {

        FindResult<Schedule> results;
        CriteriaBuilder cb;
        CriteriaQuery<Schedule> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Schedule.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Schedule>();
        //Create root
        root = cq.from(Schedule.class);

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
