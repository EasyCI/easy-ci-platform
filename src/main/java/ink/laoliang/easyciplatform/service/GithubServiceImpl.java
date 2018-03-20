package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.GithubAccount;
import ink.laoliang.easyciplatform.domain.GithubRepo;
import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.GithubTokenResponse;
import ink.laoliang.easyciplatform.exception.GithubAuthException;
import ink.laoliang.easyciplatform.repository.GithubAccountRepository;
import ink.laoliang.easyciplatform.repository.GithubRepoRepository;
import ink.laoliang.easyciplatform.repository.UserRepository;
import ink.laoliang.easyciplatform.util.CustomConfigration;
import ink.laoliang.easyciplatform.util.UserTokenByJwt;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GithubServiceImpl implements GithubService {

    @Autowired
    private CustomConfigration customConfigration;

    @Autowired
    private GithubTokenResponse githubTokenResponse;

    @Autowired
    private GithubAccountResponse githubAccountResponse;

    @Autowired
    private GithubAccount githubAccount;

    @Autowired
    private GithubAccountRepository githubAccountRepository;

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RedirectView adk() {
        String getUrl = "https://github.com/login/oauth/authorize?scope=" +
                customConfigration.getGithubAuthorizationScopes() +
                "&client_id=" +
                customConfigration.getGithubClientId();
        return new RedirectView(getUrl);
    }

    @Override
    public GithubTokenResponse callback(String code) {
        String url = "https://github.com/login/oauth/access_token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", customConfigration.getGithubClientId());
        body.add("client_secret", customConfigration.getGithubClientSecret());
        body.add("code", code);
        HttpEntity httpEntity = new HttpEntity(body, null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (responseEntity.getBody().toString().split("&")[0].split("=")[0].equals("error")) {
            throw new GithubAuthException(responseEntity.getBody().toString().split("&")[1].split("=")[1]);
        } else {
            String accessToken = responseEntity.getBody().toString().split("&")[0].split("=")[1];
            githubTokenResponse.setStatus("OK");
            githubTokenResponse.setAccessToken(accessToken);
            return githubTokenResponse;
        }
    }

    @Override
    public GithubAccountResponse updateAccount(String userToken, String accessToken) {
        try {
            GitHubClient gitHubClient = new GitHubClient().setOAuth2Token(accessToken);
            User user = new UserService(gitHubClient).getUser();

            githubAccount.setLogin(user.getLogin());
            githubAccount.setAccessToken(accessToken);
            githubAccount.setAvatarUrl(user.getAvatarUrl());
            githubAccount.setAuthorizeTo(UserTokenByJwt.parserToken(userToken, userRepository).getEmail());
            githubAccount = githubAccountRepository.save(githubAccount);
            githubAccountResponse.setGithubAccount(githubAccount);

            RepositoryService repositoryService = new RepositoryService(gitHubClient);

            // 这里每次还需要先将指定Github用户下所有repos先删除掉

            List<GithubRepo> githubRepos = new ArrayList<>();
            for (Repository repository : repositoryService.getRepositories()) {
                GithubRepo githubRepo = new GithubRepo();
                githubRepo.setId(repository.getId());
                String login = repository.getOwner().getLogin();
                if (!login.equals(user.getLogin())) continue;
                githubRepo.setLogin(login);
                githubRepo.setName(repository.getName());
                githubRepo.setCloneUrl(repository.getCloneUrl());
                List<String> branchs = new ArrayList<>();
                for (RepositoryBranch repositoryBranch : repositoryService.getBranches(repository)) {
                    branchs.add(repositoryBranch.getName());
                }
                githubRepo.setBranchs(branchs.toArray(new String[branchs.size()]));
                githubRepo = githubRepoRepository.save(githubRepo);
                githubRepos.add(githubRepo);
            }
            githubAccountResponse.setGithubRepos(githubRepos);

            return githubAccountResponse;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GithubAuthException(e.getMessage());
        }
    }
}
