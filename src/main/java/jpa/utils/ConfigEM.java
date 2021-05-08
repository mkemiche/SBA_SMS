package jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author mkemiche
 * @created 05/05/2021
 */
public class ConfigEM {

    private static final String NAME = "SMS";
    private static EntityManagerFactory emf;
    public static EntityManager createEntityManager(){
        emf = Persistence.createEntityManagerFactory(NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    public static void closeEMF(){
        if(emf != null)
            emf.close();
    }
}
