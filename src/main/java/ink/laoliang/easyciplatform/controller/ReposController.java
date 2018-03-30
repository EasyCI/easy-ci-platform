package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.GithubAuthUrlResponse;
import ink.laoliang.easyciplatform.service.ReposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/repos")
public class ReposController {

    @Autowired
    private ReposService reposService;

    @GetMapping(value = "/get_github_auth_url")
    public GithubAuthUrlResponse getGithubAuthUrl(@RequestHeader(value = "Authorization") String userToken) {
        return reposService.getGithubAuthUrl(userToken);
    }

    @GetMapping(value = "/get_github_account")
    public GithubAccountResponse getGithubAccount(@RequestHeader(value = "Authorization") String userToken) {
        return reposService.getGithubAccount(userToken);
    }

    @PostMapping(value = "/update_github_account")
    public GithubAccountResponse updateGithubAccount(@RequestHeader(value = "Authorization") String userToken,
                                                     @RequestHeader(value = "X-GitHub-Access-Token") String accessToken) {
        return reposService.updateGithubAccount(userToken, accessToken);
    }
}
