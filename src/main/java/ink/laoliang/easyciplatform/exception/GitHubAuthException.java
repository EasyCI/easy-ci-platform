package ink.laoliang.easyciplatform.exception;

public class GitHubAuthException extends RuntimeException {

    public GitHubAuthException(String description) {
        super(description, null, false, false);
    }
}
