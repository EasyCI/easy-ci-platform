package ink.laoliang.easyciplatform.domain.repository;

import ink.laoliang.easyciplatform.domain.GithubAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubAccountRepository extends JpaRepository<GithubAccount, String> {
    GithubAccount findByAuthorizeTo(String s);
}
