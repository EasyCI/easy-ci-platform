package ink.laoliang.easyciplatform.domain.response;

import ink.laoliang.easyciplatform.domain.BuildDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildDetailResponse {

    private List<BuildDetail> buildDetails;

    public BuildDetailResponse() {
    }

    public List<BuildDetail> getBuildDetails() {
        return buildDetails;
    }

    public void setBuildDetails(List<BuildDetail> buildDetails) {
        this.buildDetails = buildDetails;
    }
}
