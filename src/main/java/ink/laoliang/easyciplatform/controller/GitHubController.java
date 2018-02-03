package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.service.GitHubAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/GitHub")
public class GitHubController {

    @Autowired
    private GitHubAuthService gitHubAuthService;

    @GetMapping(value = "/authorized")
    public Object gitHubAuth(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("code");
        if (code == null) {
            // 重定向到 GitHub 第三方 App 授权认证页面
            return gitHubAuthService.adk();
        } else {
            // 返回自定义的 ResponseGitHubToken 对象
            return gitHubAuthService.callback(code);
        }
    }
}
