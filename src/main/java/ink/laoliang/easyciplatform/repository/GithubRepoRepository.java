package ink.laoliang.easyciplatform.repository;

import ink.laoliang.easyciplatform.domain.GithubRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, String> {
    List<GithubRepo> findAllByLogin(String s);

    GithubRepo findById(Long aLong);
}