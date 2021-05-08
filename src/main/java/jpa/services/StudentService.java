package jpa.services;

import jpa.dao.StudentDao;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
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
public class StudentService implements StudentDao {

    EntityManager em = null;
    StudentCourseService sts = new StudentCourseService();

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = null;
        try{
            em = ConfigEM.createEntityManager();
            students = em.createQuery("select s from Student s").getResultList();
            em.getTransaction().commit();
        }catch (IllegalArgumentException | RollbackException ex){
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ",ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        }finally {
            em.close();
            ConfigEM.closeEMF();
        }
        return students;
    }

    @Override
    public List<Student> getStudentByEmail(String email) {
        List<Student> students = null;
        try{
            em = ConfigEM.createEntityManager();
            students = em.createNamedQuery("getStudentsByEmail").setParameter("email", email).getResultList();
            em.getTransaction().commit();
        }catch (IllegalArgumentException | RollbackException ex){
            em.getTransaction().rollback();
            log.severe(String.format("Something happen on %s cause %s ",ex.getStackTrace()[0].getMethodName(), ex.getMessage()));
        }finally {
            em.close();
            ConfigEM.closeEMF();
        }
        return students;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student s = getStudentByEmail(email).get(0);
        if(s == null ){
            return false;
        }
        return s.getSPass().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, Course course) {
        StudentCourses st = new StudentCourses();
        Student student = getStudentByEmail(email).get(0);

        if(!validateStudent(email, student.getSPass())){
            return;
        }

        List<Course> courses = getStudentCourses(email);
        boolean check = courses.stream().anyMatch(c-> c.getCId() == course.getCId());
        if(check){
            System.out.println("You're already registred in this course.");
            return;
        }
        st.seteMail(email);
        st.setCourseID(course.getCId());
        sts.registerCourse(st);
    }

    @Override
    public List<Course> getStudentCourses(String email) {

        List<Course> courses = null;
        try{
            em = ConfigEM.createEntityManager();
            courses = em.createNamedQuery("getStudentCoursesByEmail2").setParameter("email", email).getResultList();
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
