package ink.laoliang.easyciplatform.domain.response;

import org.springframework.stereotype.Component;

@Component
public class GithubAuthUrlResponse {

    private String url;

    public GithubAuthUrlResponse() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
