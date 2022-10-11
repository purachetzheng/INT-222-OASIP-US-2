package sit.int221.oasipserver.exception;

public class ForbiddenException extends Exception{
    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(String message) {
        super("Unauthorized: " + message);
    }
}
