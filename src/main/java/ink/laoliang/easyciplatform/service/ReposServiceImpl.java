package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.GithubAccount;
import ink.laoliang.easyciplatform.domain.GithubRepo;
import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.response.GithubAccountResponse;
import ink.laoliang.easyciplatform.domain.response.GithubAuthUrlResponse;
import ink.laoliang.easyciplatform.exception.GithubAuthException;
import ink.laoliang.easyciplatform.repository.GithubAccountRepository;
import ink.laoliang.easyciplatform.repository.GithubRepoRepository;
import ink.laoliang.easyciplatform.repository.UserRepository;
import ink.laoliang.easyciplatform.util.CustomConfigration;
import ink.laoliang.easyciplatform.util.UserTokenByJwt;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReposServiceImpl implements ReposService {

    @Autowired
    private CustomConfigration customConfigration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;

    @Autowired
    private GithubAccountRepository githubAccountRepository;

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Autowired
    private GithubAccountResponse githubAccountResponse;

    @Autowired
    private GithubAccount githubAccount;

    @Autowired
    private GithubAuthUrlResponse githubAuthUrlResponse;

    @Override
    public GithubAuthUrlResponse getGithubAuthUrl(String userToken) {
        String url = "https://github.com/login/oauth/authorize?" +
                "scope=" + customConfigration.getGithubAuthorizationScopes() +
                "&client_id=" + customConfigration.getGithubClientId() +
                "&state=" + userToken;
        githubAuthUrlResponse.setUrl(url);
        return githubAuthUrlResponse;
    }

    @Override
    public GithubAccountResponse getGithubAccount(String userToken) {
        user = UserTokenByJwt.parserToken(userToken, userRepository);
        try {
            githubAccount = githubAccountRepository.findByAuthorizeTo(user.getEmail());
            githubAccountResponse.setGithubAccount(githubAccount);
            githubAccountResponse.setGithubRepos(githubRepoRepository.findAllByLogin(githubAccount.getLogin()));
        } catch (NullPointerException e) {
            githubAccountResponse.setGithubAccount(null);
            githubAccountResponse.setGithubRepos(null);
        }
        return githubAccountResponse;
    }

    @Override
    public GithubAccountResponse updateGithubAccount(String userToken, String accessToken) {
        try {
            GitHubClient gitHubClient = new GitHubClient().setOAuth2Token(accessToken);
            org.eclipse.egit.github.core.User user = new org.eclipse.egit.github.core.service.UserService(gitHubClient).getUser();

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
