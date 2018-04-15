package ink.laoliang.easyciplatform.domain.response;

import ink.laoliang.easyciplatform.domain.FlowDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowDetailResponse {

    private List<FlowDetail> flowDetails;

    public FlowDetailResponse() {
    }

    public List<FlowDetail> getFlowDetails() {
        return flowDetails;
    }

    public void setFlowDetails(List<FlowDetail> flowDetails) {
        this.flowDetails = flowDetails;
    }
}
