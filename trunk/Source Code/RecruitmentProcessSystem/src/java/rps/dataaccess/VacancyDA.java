/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import rps.entities.Vacancy;

/**
 *
 * @author user
 */
public class VacancyDA extends AbstractDataAccess<Vacancy> {

    public VacancyDA(EntityManager em) {
        super(Vacancy.class, em);
    }

    public FindResult<Vacancy> search(String keyword, int status) {
        FindResult<Vacancy> results;
        CriteriaBuilder cb;
        CriteriaQuery<Vacancy> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Vacancy.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Vacancy>();
        //Create root
        root = cq.from(Vacancy.class);

        //Create Predicate
        predicate = null;
        if (!keyword.equals("")) {
            predicate = cb.like(root.get("vacancyID"), "%" + keyword + "%");
            predicate = cb.or(predicate, cb.like(root.get("title"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("position"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("workingPlace"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("workType"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("description"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("skillRequirement"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("entitlement"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("degree"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("probationaryPeriod"), "%" + keyword + "%"));
        }
        if (status != -1) {
            if (predicate == null) {
                predicate = cb.equal(root.get("status"), status);
            } else {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
        }

        if (predicate != null) {
            cq.select(root).where(predicate);
            countCq.select(cb.count(root)).where(predicate);
        } else {
            cq.select(root);
            countCq.select(cb.count(root));
        }

        Query q = getEntityManager().createQuery(cq);
        Query countQ = getEntityManager().createQuery(countCq);

        int count = ((Long) countQ.getSingleResult()).intValue();

        results.addAll(q.getResultList());
        results.setCount(count);

        return results;
    }
}
