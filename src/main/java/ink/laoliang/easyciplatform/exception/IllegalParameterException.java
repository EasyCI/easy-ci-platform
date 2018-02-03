package ink.laoliang.easyciplatform.exception;

public class IllegalParameterException extends RuntimeException {

    public IllegalParameterException(String description) {
        super(description, null, false, false);
    }
}