package org.ledger.exception;

/**
 * Custom exception class to handle invalid user.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException ( String msg ) {
        super ( msg );
    }
}
