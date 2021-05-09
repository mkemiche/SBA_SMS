package jpa.services;

import jpa.entitymodels.Course;
import jpa.entitymodels.StudentCourses;
import jpa.interfaces.Delete;
import jpa.interfaces.FindAllRecords;
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
public class StudentCourseService implements FindAllRecords<Course, String>, Delete<Integer> {
    EntityManager em = null;

    /**
     * @description This method takes a Student’s Email as a parameter and would find all the courses a student is registered
     * @param email
     * @return the data as a List<Course>
     */
    @Override
    public List<Course> getAllRecords(String email) {
        List<Course> courses = null;
        try {
            em = ConfigEM.createEntityManager();
            courses = em.createNamedQuery("CoursesByStudent").setParameter("email", email).getResultList();
            em.getTransaction().commit();
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ", ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        } finally {
            em.close();
            ConfigEM.closeEMF();
        }
        return courses;
    }

    /**
     *
     * @description This method takes a Course’s id as a parameter and remove object corresponding to that id
     * @param id
     */
    @Override
    public void remove(Integer id) {
        try {
            em = ConfigEM.createEntityManager();
            StudentCourses sc = (StudentCourses) em.createQuery("select sc from StudentCourses sc where courseID = :id").setParameter("id", id).getSingleResult();
            // em.createQuery("DELETE FROM StudentCourses WHERE courseID :id")
            em.remove(sc);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ", ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        } finally {
            em.close();
            ConfigEM.closeEMF();
        }
    }
}
