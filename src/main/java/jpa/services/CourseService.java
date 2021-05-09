package jpa.services;

import com.sun.istack.Nullable;
import jpa.entitymodels.Course;
import jpa.interfaces.FindAllRecords;
import jpa.interfaces.FindCourseBy;
import jpa.utils.ConfigEM;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.List;

/**
 * The type Course service.
 *
 * @author mkemiche
 * @created 05 /05/2021
 */
@Log
public class CourseService implements FindCourseBy<Course, Integer>, FindAllRecords<Course, String> {

    /**
     * The Em.
     */
    EntityManager em = null;


    /**
     *
     * @description This method takes a Course’s id as an Integer and parses the Course list for a Course with that id
     * @param id
     * @return a Course Object.
     */
    @Override
    public List<Course> findCourseBy(Integer id) {
        List<Course> courses = null;
        try{
            em = ConfigEM.createEntityManager();
            courses = em.createNamedQuery("getCourseById").setParameter("id", id).getResultList();
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

    /**
     *
     * @description This method takes no parameter and returns every Course in the table.
     * @param @Nullable String value
     * @return the data as a List<Course>
     */
    @Override
    public List<Course> getAllRecords(@Nullable String s) {

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
}
