package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.request.LoginRequest;
import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
import ink.laoliang.easyciplatform.domain.User;
import org.springframework.web.servlet.view.RedirectView;

public interface UserService {

    User register(User user);

    LoginResponse login(LoginRequest loginRequest);

    User logout(String userToken);

    User changePassword(String userToken, String newPassword);

    GithubAccountResponse getGithubAccount(String userToken);
}
