package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.GithubAuthUrlResponse;

public interface ReposService {
    GithubAuthUrlResponse getGithubAuthUrl(String userToken);

    GithubAccountResponse getGithubAccount(String userToken);

    GithubAccountResponse updateGithubAccount(String userToken, String accessToken);
}
