package rps.business;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Windows7
 */
public abstract class AbstractService {
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    
    public AbstractService(){
        String persistenceUnitName = "RecruitmentProcessSystemPU";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = factory.createEntityManager();
    }
    
    public void beginTransaction(){
        em.getTransaction().begin();
    }
    
    public void commitTransaction(){
        em.getTransaction().commit();
    }

}
