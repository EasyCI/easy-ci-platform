package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;

public interface UserService {
    User register(User user);

    LoginResponse login(String email, String password);

    User changePassword(String userToken, String oldPassword, String newPassword);
}
