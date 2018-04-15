package ink.laoliang.easyciplatform.exception;

public class GithubHookException extends RuntimeException {

    public GithubHookException(String description) {
        super(description, null, false, false);
    }
}
