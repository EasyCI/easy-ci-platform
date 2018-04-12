package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.response.FlowDetailResponse;

public interface DashboardService {
    FlowDetailResponse getFlowDetails(String userToken);
}
