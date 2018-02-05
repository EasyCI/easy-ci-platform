package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
import ink.laoliang.easyciplatform.domain.User;

public interface UserService {

    User register(User user);

    LoginResponse login(String email, String password);

    User logout(String userToken);

    User changePassword(String userToken, String newPassword);

    GithubAccountResponse getGithubAccount(String userToken);
}
