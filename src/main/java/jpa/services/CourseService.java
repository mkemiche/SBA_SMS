package jpa.services;

import jpa.dao.CourseDao;
import jpa.entitymodels.Course;
import jpa.utils.ConfigEM;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.List;

/**
 * @author mkemiche
 * @created 05/05/2021
 */
@Log
public class CourseService implements CourseDao {

    EntityManager em = null;
    @Override
    public List<Course> getAllCourses() {

        List<Course> courses = null;
        try{
            em = ConfigEM.createEntityManager();
            courses = em.createQuery("select c from Course c").getResultList();
            em.getTransaction().commit();
        }catch (IllegalArgumentException | RollbackException ex){
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ",ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        }finally {
            em.close();
            ConfigEM.closeEMF();
        }
        return courses;
    }

    @Override
    public List<Course> getCourseById(int number) {
        List<Course> courses = null;
        try{
            em = ConfigEM.createEntityManager();
            courses = em.createNamedQuery("getCourseById").setParameter("id", number).getResultList();
            em.getTransaction().commit();
        }catch (IllegalArgumentException | RollbackException ex){
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ",ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        }finally {
            em.close();
            ConfigEM.closeEMF();
        }
        return courses;
    }
}
