package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.request.LoginRequest;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
import ink.laoliang.easyciplatform.domain.User;

public interface UserService {

    User register(User user);

    LoginResponse login(LoginRequest loginRequest);

    User logout(String userToken);
}
