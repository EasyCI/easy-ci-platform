package ink.laoliang.easyciplatform.exception;

public class IllegalUserTokenException extends RuntimeException {

    public IllegalUserTokenException(String description) {
        super(description, null, false, false);
    }
}