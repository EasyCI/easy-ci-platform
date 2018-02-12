package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.Plugin;
import ink.laoliang.easyciplatform.domain.request.FlowDeleteRequest;

import java.util.List;

public interface FlowService {
    List<Plugin> getPlugins();

    Flow createFlow(Flow flow, String accessToken);

    Flow editFlow(Flow flow);

    List<Flow> getAllFlow();

    void deleteFlow(FlowDeleteRequest flowDeleteRequest, String accessToken);
}
