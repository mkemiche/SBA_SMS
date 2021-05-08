package jpa.services;

import jpa.dao.StudentCourseDao;
import jpa.entitymodels.Course;
import jpa.entitymodels.StudentCourses;
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
public class StudentCourseService implements StudentCourseDao {
    EntityManager em = null;
    @Override
    public List<Course> getAllStudentCourses(String email) {


        List<Course> courses = null;
        try{
            em = ConfigEM.createEntityManager();
            courses = em.createNamedQuery("CoursesByStudent").setParameter("email", email).getResultList();
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
    public void registerCourse(StudentCourses studentCourses) {
        try{
            em = ConfigEM.createEntityManager();
            em.persist(studentCourses);
            em.getTransaction().commit();
        }catch (IllegalArgumentException | RollbackException ex){
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ",ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        }finally {
            em.close();
            ConfigEM.closeEMF();
        }
    }


}
