package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.CourseAlreadyRegistredException;
import jpa.exceptions.StudentNotFoundException;
import jpa.exceptions.UserValidationFailedException;

import java.util.List;

/**
 * @author mkemiche
 * @created 05/05/2021
 */
public interface StudentDao {

    List<Student> getStudentByEmail(String email);
    void registerStudentToCourse(Student student, Course course) throws StudentNotFoundException, UserValidationFailedException, CourseAlreadyRegistredException;
    List<Course> getStudentCourses(String email);
}
