package ink.laoliang.easyciplatform.repository;

import ink.laoliang.easyciplatform.domain.BuildDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildDetailRepository extends JpaRepository<BuildDetail, String> {
    List<BuildDetail> findAllByFlowId(String flowId, Sort orders);

    List<BuildDetail> findAllByFlowIdAndIsSuccess(String flowId, Boolean isSuccess);

    Integer deleteAllByFlowId(String flowId);
}
