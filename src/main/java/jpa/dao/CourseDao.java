package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

/**
 * @author mkemiche
 * @created 05/05/2021
 */
public interface CourseDao {

    List<Course> getAllCourses();

    List<Course> getCourseById(int number);

}
