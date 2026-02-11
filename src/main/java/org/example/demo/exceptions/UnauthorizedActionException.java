package org.example.demo.exceptions;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String s) {
        super(s);
    }

}
