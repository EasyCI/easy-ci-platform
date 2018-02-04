package ink.laoliang.easyciplatform.domain;

import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Component
public class GithubAccount extends BaseEntity {

    @Id
    private String login;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String avatarUrl;

    @Column(nullable = false)
    private String authorizeTo;

    public GithubAccount() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAuthorizeTo() {
        return authorizeTo;
    }

    public void setAuthorizeTo(String authorizeTo) {
        this.authorizeTo = authorizeTo;
    }
}
