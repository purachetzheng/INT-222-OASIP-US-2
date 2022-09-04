package sit.int221.oasipserver.exception;

public class PasswordException extends Exception{
    public PasswordException() {
        super("Invalid password");
    }

    public PasswordException(String message) {
        super("Invalid password: " + message);
    }
}
