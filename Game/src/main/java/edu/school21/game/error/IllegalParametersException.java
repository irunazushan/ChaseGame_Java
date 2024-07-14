package edu.school21.game.error;

public class IllegalParametersException extends RuntimeException {
    public IllegalParametersException(String message) {
        super(message);
    }
}