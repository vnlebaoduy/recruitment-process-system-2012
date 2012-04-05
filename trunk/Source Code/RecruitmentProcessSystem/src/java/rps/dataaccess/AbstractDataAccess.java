package rps.dataaccess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

/**
 *
 * @author namtp_C03916
 */
public abstract class AbstractDataAccess<T> {

    private Class<T> entityClass;
    private EntityManager em;

    public AbstractDataAccess(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public FindResult<T> findAbsolutely(
            String propertyName, Object propertyValue) {
        return findAbsolutely(
                new String[]{propertyName},
                new Object[]{propertyValue},
                null,
                null,
                -1,
                -1);
    }

    public FindResult<T> findAbsolutely(
            String propertyName, Object propertyValue,
            String propertyOrderName, String propertyOrderDirection) {
        return findAbsolutely(
                new String[]{propertyName},
                new Object[]{propertyValue},
                new String[]{propertyOrderName},
                new String[]{propertyOrderDirection},
                -1,
                -1);
    }

    public FindResult<T> findAbsolutely(
            String propertyName, Object propertyValue,
            String propertyOrderName, String propertyOrderDirection,
            int fromIndex, int toIndex) {
        return findAbsolutely(
                new String[]{propertyName},
                new Object[]{propertyValue},
                new String[]{propertyOrderName},
                new String[]{propertyOrderDirection},
                fromIndex,
                toIndex);
    }    
    
    public FindResult<T> findAbsolutely(
            String[] propertyNames, Object[] propertyValues,
            String[] propertyOrderNames, String[] propertyOrderDirections,
            int fromIndex, int toIndex) {

        propertyNames = propertyNames == null ? new String[0] : propertyNames;
        propertyValues = propertyValues == null ? new Object[0] : propertyValues;
        propertyOrderNames = propertyOrderNames == null ? new String[0] : propertyOrderNames;
        propertyOrderDirections = propertyOrderDirections == null ? new String[0] : propertyOrderDirections;

        FindResult<T> results;
        CriteriaBuilder cb;
        CriteriaQuery<T> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;
        Order[] orders;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(entityClass);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<T>();
        results.setPropertyNames(propertyNames);
        results.setPropertyValues(propertyValues);
        results.setPropertyOrderNames(propertyOrderNames);
        results.setPropertyOrderDirections(propertyOrderDirections);
        results.setFromIndex(fromIndex);
        results.setToIndex(toIndex);
        //Create root
        root = cq.from(entityClass);
        //Create Predicate
        Predicate[] predicates = new Predicate[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            Predicate predicateTemp = cb.equal(root.get(propertyNames[i]), propertyValues[i]);
            predicates[i] = predicateTemp;
        }
        predicate = cb.and(predicates);
        //Create OrderList
        orders = new Order[propertyOrderNames.length];
        for (int i = 0; i < propertyOrderNames.length; i++) {
            Order orderTemp;
            if (propertyOrderDirections[i].equals("DESC")) {
                orderTemp = cb.desc(root.get(propertyOrderNames[i]));
            } else {
                orderTemp = cb.asc(root.get(propertyOrderNames[i]));
            }
            orders[i] = orderTemp;
        }

        cq.select(root).where(predicate).orderBy(orders);
        countCq.select(cb.count(root)).where(predicate);

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

    public FindResult<T> findRelatively(
            String[] propertyNames, Object[] propertyValues,
            String[] propertyOrderNames, String[] propertyOrderDirections,
            int fromIndex, int toIndex) {

        propertyNames = propertyNames == null ? new String[0] : propertyNames;
        propertyValues = propertyValues == null ? new Object[0] : propertyValues;
        propertyOrderNames = propertyOrderNames == null ? new String[0] : propertyOrderNames;
        propertyOrderDirections = propertyOrderDirections == null ? new String[0] : propertyOrderDirections;

        FindResult<T> results;
        CriteriaBuilder cb;
        CriteriaQuery<T> cq;
        CriteriaQuery<Long> countCq;
        Root root;
        Predicate predicate;
        Order[] orders;

        //Create CriteraBuilder
        cb = getEntityManager().getCriteriaBuilder();
        //Create CriteraQuery
        cq = cb.createQuery(entityClass);
        countCq = cb.createQuery(Long.class);
        //Create results
        results = new FindResult<T>();
        results.setPropertyNames(propertyNames);
        results.setPropertyValues(propertyValues);
        results.setPropertyOrderNames(propertyOrderNames);
        results.setPropertyOrderDirections(propertyOrderDirections);
        results.setFromIndex(fromIndex);
        results.setToIndex(toIndex);
        //Create root
        root = cq.from(entityClass);
        //Create Predicate
        Predicate[] predicates = new Predicate[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyValues[i] instanceof String) {
                Predicate predicateTemp = cb.like(root.get(propertyNames[i]), "%" + propertyValues[i] + "%");
                predicates[i] = predicateTemp;
            } else {
                Predicate predicateTemp = cb.equal(root.get(propertyNames[i]), propertyValues[i]);
                predicates[i] = predicateTemp;
            }
        }
        predicate = cb.or(predicates);
        //Create OrderList
        orders = new Order[propertyOrderNames.length];
        for (int i = 0; i < propertyOrderNames.length; i++) {
            Order orderTemp;
            if (propertyOrderDirections[i].equals("DESC")) {
                orderTemp = cb.desc(root.get(propertyOrderNames[i]));
            } else {
                orderTemp = cb.asc(root.get(propertyOrderNames[i]));
            }
            orders[i] = orderTemp;
        }

        cq.select(root).where(predicate).orderBy(orders);
        countCq.select(cb.count(root)).where(predicate);

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

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);

        return ((Long) q.getSingleResult()).intValue();
    }
    
    
}
