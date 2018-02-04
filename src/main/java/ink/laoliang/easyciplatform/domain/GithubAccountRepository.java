package ink.laoliang.easyciplatform.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubAccountRepository extends JpaRepository<GithubAccount, String> {
    GithubAccount findByAuthorizeTo(String s);
}
