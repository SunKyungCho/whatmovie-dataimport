package me.toyproject.whatmoviedataimport.exception;

public class ExceedUsageException extends RuntimeException {
    public ExceedUsageException(String message) {
        super(message);
    }
}