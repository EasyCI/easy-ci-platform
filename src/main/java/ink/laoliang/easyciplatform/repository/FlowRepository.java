package ink.laoliang.easyciplatform.repository;

import ink.laoliang.easyciplatform.domain.Flow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowRepository extends JpaRepository<Flow, String> {
    List<Flow> findAllByUserEmail(String s);
}
