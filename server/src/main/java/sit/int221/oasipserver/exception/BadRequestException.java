package sit.int221.oasipserver.exception;

public class BadRequestException extends Exception{
    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(String message) {
        super("Bad request: " + message);
    }
}
