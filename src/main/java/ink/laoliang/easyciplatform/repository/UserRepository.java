package ink.laoliang.easyciplatform.repository;

import ink.laoliang.easyciplatform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String s);
}
