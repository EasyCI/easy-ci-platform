package ink.laoliang.easyciplatform.domain.response;

import ink.laoliang.easyciplatform.domain.BaseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginResponse {

    private String userToken;

    private BaseEntity user;

    public LoginResponse() {
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public BaseEntity getUser() {
        return user;
    }

    public void setUser(BaseEntity user) {
        this.user = user;
    }
}
