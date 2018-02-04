package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping(value = "/get_access_token")
    public Object getAccessToken(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("code");
        if (code == null) {
            // 重定向到 GitHub 第三方 App 授权认证页面
            return githubService.adk();
        } else {
            // 返回自定义的 GithubTokenResponse 对象
            return githubService.callback(code);
        }
    }

    @PostMapping(value = "/update_account")
    public GithubAccountResponse updateAccount(@RequestHeader(value = "Authorization") String userToken,
                                               @RequestHeader(value = "X-GitHub-Access-Token") String accessToken) {
        return githubService.updateAccount(userToken, accessToken);
    }
}
