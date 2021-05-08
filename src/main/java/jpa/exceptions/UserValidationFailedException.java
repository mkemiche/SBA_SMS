package jpa.exceptions;

/**
 * @author mkemiche
 * @created 07/05/2021
 */
public class UserValidationFailedException extends Exception {

    public UserValidationFailedException(String message) {
        super(message);
    }
}
