package ink.laoliang.easyciplatform.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, String> {
    List<GithubRepo> findAllByLogin(String s);
}
