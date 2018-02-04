package ink.laoliang.easyciplatform.domain.response;

import org.springframework.stereotype.Component;

@Component
public class GithubTokenResponse {

    private String status;

    private String accessToken;

    public GithubTokenResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
