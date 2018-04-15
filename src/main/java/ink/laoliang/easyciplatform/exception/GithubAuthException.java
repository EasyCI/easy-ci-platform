package ink.laoliang.easyciplatform.exception;

public class GithubAuthException extends RuntimeException {

    public GithubAuthException(String description) {
        super(description, null, false, false);
    }
}
