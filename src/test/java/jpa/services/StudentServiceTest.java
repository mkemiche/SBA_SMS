package jpa.services;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.CourseAlreadyRegistredException;
import jpa.exceptions.CourseNotFoundException;
import jpa.exceptions.StudentNotFoundException;
import jpa.exceptions.UserValidationFailedException;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
class StudentServiceTest {

    StudentService ss = new StudentService();
    CourseService sc = new CourseService();
    StudentCourseService scs = new StudentCourseService();
    @Test
    void getAllStudents() {
        List<Student> studentList = ss.getAllStudents();
        Student student = new Student("aiannitti7@is.gd","Alexandra Iannitti", "TWP4hf5j", new ArrayList<>());
        assertEquals(10, studentList.size());
        assertEquals(student, studentList.get(0));
    }

    @Test
    void getStudentByEmail() {
        Student actualStudent = new Student("aiannitti7@is.gd","Alexandra Iannitti", "TWP4hf5j", new ArrayList<>());
        Student expectedStudent = ss.getStudentByEmail(actualStudent.getSEmail()).get(0);
        assertEquals(actualStudent.getSName(), expectedStudent.getSName());
        assertEquals(actualStudent.getSPass(), expectedStudent.getSPass());
    }

    @Test
    void registerStudentToCourse() throws UserValidationFailedException, StudentNotFoundException, CourseAlreadyRegistredException, CourseNotFoundException {
        Student student = new Student("cstartin3@flickr.com", "Clem Startin", "XYHzJ1S", new ArrayList<>());
        Course course = new Course(7,"Object Oriented Programming","Giselle Ardy");

        //remove existing course
        scs.removeRegistredCourse(course.getCId());

        //register course
        //test existing course in database
        ss.registerStudentToCourse(student, course);
        var registredCourse = scs.getAllStudentCourses(student.getSEmail()).stream().filter(c->c.getCId() == course.getCId()).findFirst().orElse(null);
        assertEquals(course, registredCourse);

        //Student can't registre the same course twice
        //try to register the same existing course
        //thorw exception CourseAlreadyRegistredException.
        assertThrows(CourseAlreadyRegistredException.class, ()-> ss.registerStudentToCourse(student, course));

        //student must have email and password valid
        String wrongPassword = "1234";
        student.setSPass(wrongPassword);
        assertThrows(UserValidationFailedException.class, ()-> ss.registerStudentToCourse(student, course));
    }
}