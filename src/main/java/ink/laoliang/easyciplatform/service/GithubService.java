package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.GithubTokenResponse;
import org.springframework.web.servlet.view.RedirectView;

public interface GithubService {
    RedirectView adk();

    GithubTokenResponse callback(String code);

    GithubAccountResponse updateAccount(String userToken, String accessToken);
}
