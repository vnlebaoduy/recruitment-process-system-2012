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
import rps.entities.Applicant;

/**
 *
 * @author user
 */
public class ApplicantDA extends AbstractDataAccess<Applicant> {

    public ApplicantDA(EntityManager em) {
        super(Applicant.class, em);
    }
    public FindResult<Applicant> search(String keyword, int status) {
        FindResult<Applicant> results;
        CriteriaBuilder cb;
        CriteriaQuery<Applicant> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(Applicant.class);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<Applicant>();
        //Create root
        root = cq.from(Applicant.class);

        //Create Predicate
        predicate = null;
        if (!keyword.equals("")) {
            predicate = cb.like(root.get("applicantID"), "%" + keyword + "%");
            predicate = cb.or(predicate, cb.like(root.get("firstName"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("lastName"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("phoneNumber"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("email"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("address"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("language"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("degree"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("skill"), "%" + keyword + "%"));
            predicate = cb.or(predicate, cb.like(root.get("award"), "%" + keyword + "%"));
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
