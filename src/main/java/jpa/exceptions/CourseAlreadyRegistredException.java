package jpa.exceptions;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
public class CourseAlreadyRegistredException extends Exception {

    public CourseAlreadyRegistredException(String message) {
        super(message);
    }
}
