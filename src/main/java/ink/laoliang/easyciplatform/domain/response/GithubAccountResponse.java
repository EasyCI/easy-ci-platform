package ink.laoliang.easyciplatform.domain.response;

import ink.laoliang.easyciplatform.domain.GithubAccount;
import ink.laoliang.easyciplatform.domain.GithubRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubAccountResponse {

    private GithubAccount githubAccount;

    private List<GithubRepo> githubRepos;

    public GithubAccountResponse() {
    }

    public GithubAccount getGithubAccount() {
        return githubAccount;
    }

    public void setGithubAccount(GithubAccount githubAccount) {
        this.githubAccount = githubAccount;
    }

    public List<GithubRepo> getGithubRepos() {
        return githubRepos;
    }

    public void setGithubRepos(List<GithubRepo> githubRepos) {
        this.githubRepos = githubRepos;
    }
}
