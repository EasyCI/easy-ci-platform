package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
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
    public LoginResponse login(@RequestParam String email,
                               @RequestParam String password) {
        return userService.login(email, password);
    }

    @PostMapping(value = "/change_password")
    public User changePassword(@RequestHeader(value = "Authorization") String userToken,
                               @RequestParam String oldPassword,
                               @RequestParam String newPassword) {
        return userService.changePassword(userToken, oldPassword, newPassword);
    }

    @GetMapping(value = "/get_github_account")
    public GithubAccountResponse getGithubAccount(@RequestHeader(value = "Authorization") String userToken) {
        return userService.getGithubAccount(userToken);
    }
}
