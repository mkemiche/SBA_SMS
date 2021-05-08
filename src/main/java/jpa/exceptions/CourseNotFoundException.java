package jpa.exceptions;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
public class CourseNotFoundException extends Exception {

    public CourseNotFoundException(String message) {
        super(message);
    }
}
