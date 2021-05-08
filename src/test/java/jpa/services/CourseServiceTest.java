package jpa.services;

import jpa.entitymodels.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
class CourseServiceTest {

    CourseService cs = new CourseService();

    @Test
    void getAllCourses() {
        List<Course> courseList = cs.getAllCourses();
        Course course = new Course(5,"Physics", "Dani Swallow");
        assertEquals(10, courseList.size());
        assertEquals(course, courseList.get(4));
    }

    @Test
    void getCourseById() {
        Course actualCourse = new Course(8,"Data Structures", "Carolan Stoller");
        Course ExpectedCourse = cs.getCourseById(actualCourse.getCId()).get(0);
        assertEquals(actualCourse.getCName(), ExpectedCourse.getCName());
        assertEquals(actualCourse.getCInstructorName(), ExpectedCourse.getCInstructorName());
    }
}