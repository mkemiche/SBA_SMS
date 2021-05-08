package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.StudentCourses;

import java.util.List;

/**
 * @author mkemiche
 * @created 05/05/2021
 */
public interface StudentCourseDao {

    List<Course> getAllStudentCourses(String email);
    void registerCourse(StudentCourses studentCourses);
}
