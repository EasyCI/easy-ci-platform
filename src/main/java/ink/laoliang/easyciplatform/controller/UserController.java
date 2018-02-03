package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.request.LoginRequest;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping(value = "/logout")
    public User logout(@RequestHeader String authorization) {
        return userService.logout(authorization);
    }
}
