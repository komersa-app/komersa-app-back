package komersa.exception;

public class ProtectedResourceException extends RuntimeException{
    public ProtectedResourceException() {
        super("Resource reserved for Admin");
    }
}
