package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GitHubTokenResponse;
import org.springframework.web.servlet.view.RedirectView;

public interface GitHubAuthService {
    RedirectView adk();

    GitHubTokenResponse callback(String code);
}
