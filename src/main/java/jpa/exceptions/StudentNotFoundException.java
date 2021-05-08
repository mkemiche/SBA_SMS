package jpa.exceptions;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
public class StudentNotFoundException extends Exception{

    public StudentNotFoundException(String message) {
        super(message);
    }
}
