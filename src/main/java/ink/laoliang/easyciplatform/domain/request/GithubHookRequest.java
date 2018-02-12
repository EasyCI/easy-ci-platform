package ink.laoliang.easyciplatform.domain.request;

public class GithubHookRequest {

    private String ref;

    public GithubHookRequest() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
