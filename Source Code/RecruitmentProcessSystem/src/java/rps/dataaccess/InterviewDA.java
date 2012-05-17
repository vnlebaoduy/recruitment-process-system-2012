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
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import rps.entities.Applicant;
import rps.entities.Employee;
import rps.entities.Interview;
import rps.entities.Vacancy;
import rps.entities.statistic.VacancyStatistic;

/**
 *
 * @author Bach Luong
 */
public class InterviewDA extends AbstractDataAccess<Interview> {

    private EntityManager em;

    public InterviewDA(EntityManager em) {
        super(Interview.class, em);
        this.em = em;
    }

    public FindResult<Interview> searchInterview(Employee employee, Date startedTime,
            Date endedTime, int status) {

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
            predicates.add(cb.equal(root.get("employee"), employee));
        }
        if (startedTime != null && endedTime != null) {
            predicates.add(cb.between(root.get("startedTime"),
                    startedTime, endedTime));
        }
        if (status != -1) {
            predicates.add(cb.equal(root.get("status"), status));
        }
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
        cq.select(root).where(predicate).orderBy(cb.asc(root.get("startedTime")));
        countCq.select(cb.count(root)).where(predicate);

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }

    public FindResult<Interview> searchInterview(Applicant applicant, Date endedTime) {

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

        predicates.add(cb.equal(root.get("applicant"), applicant));
        predicates.add(cb.greaterThanOrEqualTo(root.get("startedTime"), endedTime));

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

    public FindResult<Interview> searchInterviewNotRemove(Vacancy vacancy,
            Applicant applicant, int AVStatus) {

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

        predicates.add(cb.equal(root.get("applicant"), applicant));
        predicates.add(cb.equal(root.get("vacancy"), vacancy));
        predicates.add(cb.equal(root.get("aVStatus"), AVStatus));
        predicates.add(cb.notEqual(root.get("status"), 1));

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

    public FindResult<Interview> searchInterview(Vacancy vacancy, Date endedTime) {

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

        predicates.add(cb.equal(root.get("vacancy"), vacancy));
        predicates.add(cb.greaterThanOrEqualTo(root.get("startedTime"), endedTime));

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

    public List<VacancyStatistic> findFavoriteVacancy(int num) {
        List<VacancyStatistic> result = new ArrayList<VacancyStatistic>();
        if (num > 0) {
            TypedQuery<VacancyStatistic> query = em.createQuery(
                    "SELECT i.vacancy, COUNT(i.vacancy) AS Num"
                    + " FROM Interview i"
                    + " GROUP BY i.vacancy"
                    + " ORDER BY Num DESC",
                    VacancyStatistic.class);
            List<VacancyStatistic> list = query.getResultList();
            if (list.size() > num) {
                list = list.subList(0, num);
            }
            for (Object obj : list) {
                Object[] objs = (Object[]) obj;
                VacancyStatistic vs = new VacancyStatistic();
                vs.setVacancy((Vacancy) objs[0]);
                vs.setNumberApplicants(Long.parseLong(objs[1].toString()));
                result.add(vs);
            }
        }
        return result;
    }

    public int findNumberRegisteredApplicant(Date start, Date end) {
        int result = -1;
        TypedQuery<Integer> query = em.createQuery(
                "SELECT COUNT(i.applicant) AS Num"
                + " FROM Interview i"
                + " WHERE i.startedTime BETWEEN :start AND :end",
                Integer.class)
                .setParameter("start", start, TemporalType.DATE)
                .setParameter("end", end, TemporalType.DATE);
        List<Integer> list = query.getResultList();
        for (Object obj : list) {
            Long value = (Long) obj;
            result = Integer.parseInt(value.toString());
        }
        return result;
    }
}
