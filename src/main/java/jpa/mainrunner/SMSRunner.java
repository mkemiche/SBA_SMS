/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import static java.lang.System.out;

import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.services.CourseService;
import jpa.services.StudentCourseService;
import jpa.services.StudentService;

/**1
 * 
 * @author Harry
 *
 */
public class SMSRunner {

	private Scanner sin;
	private StringBuilder sb;

	private CourseService courseService;
	private StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sin = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SMSRunner sms = new SMSRunner();
		sms.run();
	}

	private void run() {
		// Login or quit
		switch (menu1()) {
		case 1:
			if (studentLogin()) {
				registerMenu();
			}
			break;
		case 2:
			out.println("Goodbye!");
			break;

		default:

		}
	}

	private int menu1() {
		sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		return sin.nextInt();
	}

	private boolean studentLogin() {
		boolean retValue = false;
		out.print("Enter your email address: ");
		String email = sin.next();
		out.print("Enter your password: ");
		String password = sin.next();

		List<Student> students = studentService.getStudentByEmail(email);
		if (!students.isEmpty()) {
			currentStudent = students.get(0);
		}

		if (currentStudent != null && currentStudent.getSPass().equals(password)) {
			List<Course> courses = studentService.getStudentCourses(email);
			out.println("MyClasses");
			out.printf("%-10s%-20S%-15s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
			for (Course course : courses) {
				out.println(course);
			}
			retValue = true;
		} else {
			out.println("User Validation failed. GoodBye!");
		}
		return retValue;
	}

	private void registerMenu() {
		sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		switch (sin.nextInt()) {
		case 1:
			List<Course> allCourses = courseService.getAllCourses();
			List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
			allCourses.removeAll(studentCourses);
			out.printf("%-10s%-20S%-15s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
			for (Course course : allCourses) {
				out.println(course);
			}
			out.println();
			out.print("Enter Course Number: ");
			int number = sin.nextInt();
			List<Course> newCourse = courseService.getCourseById(number);

			if (!newCourse.isEmpty()) {
				studentService.registerStudentToCourse(currentStudent.getSEmail(), newCourse.get(0));
				Student temp = studentService.getStudentByEmail(currentStudent.getSEmail()).get(0);
				
				StudentCourseService scService = new StudentCourseService();
				List<Course> sCourses = scService.getAllStudentCourses(temp.getSEmail());
				

				out.println("MyClasses :");
				out.printf("%-10s%-20S%-15s\n", "COURSE ID", "COURSE NAME", "INSTRUCTOR NAME");
				for (Course course : sCourses) {
					out.println(course);

				}
			} else {
				out.println("Course not found. GoodBye!");
			}
			break;
		case 2:
		default:
			out.println("Goodbye!");
		}
	}
}
