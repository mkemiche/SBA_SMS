/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.CourseAlreadyRegistredException;
import jpa.exceptions.CourseNotFoundException;
import jpa.exceptions.StudentNotFoundException;
import jpa.exceptions.UserValidationFailedException;
import jpa.services.CourseService;
import jpa.services.StudentCourseService;
import jpa.services.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.out;

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
	private Optional<Student> currentStudent;

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
		try{
			sms.run();
		}catch (UserValidationFailedException | StudentNotFoundException | CourseNotFoundException | CourseAlreadyRegistredException ex){
			out.println(ex.getMessage()+" - "+ex.getStackTrace()[0].getMethodName());
		}

	}

	/**
	 * @description which allows the user to enter his/her email and password and check whether or not those credentials are valid, in order to log in.
	 * 				After a validation credential, this methdo allow user to displays and prompt the student to select one of the following two additional numeric (1 or 2)
	 * @throws UserValidationFailedException
	 * @throws StudentNotFoundException
	 * @throws CourseNotFoundException
	 * @throws CourseAlreadyRegistredException
	 */
	private void run() throws UserValidationFailedException, StudentNotFoundException, CourseNotFoundException, CourseAlreadyRegistredException {
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

	/**
	 * @description this method check credential user. If the credentials are invalid the program should end with appropriate message to the student.
	 *				If the credentials are valid, the student is logged in and all the classes the Student is registered to should be displayed.
	 * @return
	 * @throws StudentNotFoundException
	 * @throws UserValidationFailedException
	 */
	private boolean studentLogin() throws StudentNotFoundException, UserValidationFailedException {
		boolean retValue = false;
		out.print("Enter your email address: ");
		String email = sin.next();
		out.print("Enter your password: ");
		String password = sin.next();

		currentStudent = studentService.findStudentBy(email).stream().findFirst();
		if(currentStudent.isEmpty()) {
			out.println("Student not found. GoodBye!");
			throw new StudentNotFoundException("Student does not exist with entered email : "+ email);
		}

		if (currentStudent.get().getSPass().equals(password)) {
			List<Course> courses = studentService.findCourseBy(email);
			out.println("MyClasses");
			out.printf("%-10s%-20S%-15s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
			courses.forEach(out::println);
			retValue = true;
		} else {
			out.println("User Validation failed. GoodBye!");
			throw new UserValidationFailedException("User Validation failed.");
		}
		return retValue;
	}

	/**
	 * @description Register to Class: Which displays all the courses in the database and allows the student to select a course in which the student wished to be registered to.
	 * 				If the Student is already registered in that course, display the message "You are already registered in that course!",
	 * 				otherwise, register the student to that course and save this result in your database. Also show the updated registered courses list for that student.
	 * 				After that end the program with appropriate message.
	 * @throws CourseNotFoundException
	 * @throws StudentNotFoundException
	 * @throws UserValidationFailedException
	 * @throws CourseAlreadyRegistredException
	 */
	private void registerMenu() throws CourseNotFoundException, StudentNotFoundException, UserValidationFailedException, CourseAlreadyRegistredException {
		sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		switch (sin.nextInt()) {
		case 1:
			List<Course> allCourses = courseService.getAllRecords(null);
			List<Course> studentCourses = studentService.findCourseBy(currentStudent.get().getSEmail());
			allCourses.removeAll(studentCourses);
			out.printf("%-10s%-20S%-15s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
			allCourses.forEach(out::println);
			out.println();
			out.print("Enter Course Number: ");
			int number = sin.nextInt();
			List<Course> newCourse = courseService.findCourseBy(number);

			if (!newCourse.isEmpty()) {
				studentService.register(currentStudent.get(), newCourse.get(0));
				Student temp = studentService.findStudentBy(currentStudent.get().getSEmail()).get(0);
				
				StudentCourseService scService = new StudentCourseService();
				List<Course> sCourses = scService.getAllRecords(temp.getSEmail());
				

				out.println("MyClasses :");
				out.printf("%-10s%-20S%-15s\n", "COURSE ID", "COURSE NAME", "INSTRUCTOR NAME");
				sCourses.forEach(out::println);
			} else {
				out.println("Course not found. GoodBye!");
				throw new CourseNotFoundException("Selected course does not exist.");
			}
			break;
		case 2:
		default:
			out.println("Goodbye!");
		}
	}
}
