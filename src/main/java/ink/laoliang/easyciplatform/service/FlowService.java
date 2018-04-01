package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.request.DeleteFlowRequest;
import ink.laoliang.easyciplatform.domain.response.PluginsResponse;

import java.util.List;

public interface FlowService {
    PluginsResponse getPlugins();

    Flow createFlow(Flow flow, String accessToken);

    Flow editFlow(Flow flow);

    List<Flow> getAllFlow(String userToken);

    void deleteFlow(DeleteFlowRequest deleteFlowRequest, String accessToken);
}
