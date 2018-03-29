package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;

public interface ReposService {
    String getGithubAuthUrl(String userToken);

    GithubAccountResponse getGithubAccount(String userToken);

    GithubAccountResponse updateGithubAccount(String userToken, String accessToken);
}
