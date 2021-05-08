package jpa.services;

import jpa.dao.StudentDao;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.exceptions.CourseAlreadyRegistredException;
import jpa.exceptions.StudentNotFoundException;
import jpa.exceptions.UserValidationFailedException;
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


    private boolean validateStudent(String email, String password) throws StudentNotFoundException {
        List<Student> students = getStudentByEmail(email);
        if(students.isEmpty()){
            throw new StudentNotFoundException("Student does not exist with entered email : "+ email);
        }
        return students.get(0).getSPass().equals(password);
    }

    @Override
    public void registerStudentToCourse(Student student, Course course) throws StudentNotFoundException, UserValidationFailedException, CourseAlreadyRegistredException {
        StudentCourses st = new StudentCourses();

        if(!validateStudent(student.getSEmail(), student.getSPass())){
            throw new UserValidationFailedException("User Validation failed.");
        }

        List<Course> courses = getStudentCourses(student.getSEmail());
        boolean check = courses.stream().anyMatch(c-> c.getCId() == course.getCId());
        if(check){
            System.out.println("You're already registred in this course.");
            throw new CourseAlreadyRegistredException("You're already registred in this course with id : "+course.getCId());
        }
        st.seteMail(student.getSEmail());
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
