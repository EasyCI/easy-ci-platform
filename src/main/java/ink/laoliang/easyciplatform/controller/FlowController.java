package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.request.DeleteFlowRequest;
import ink.laoliang.easyciplatform.domain.response.PluginsResponse;
import ink.laoliang.easyciplatform.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/flow")
public class FlowController {

    @Autowired
    private FlowService flowService;

    @GetMapping(value = "/get_plugins")
    public PluginsResponse getPlugins() {
        return flowService.getPlugins();
    }

    @PostMapping(value = "/create")
    public Flow createFlow(@RequestBody Flow flow,
                           @RequestHeader(value = "X-GitHub-Access-Token") String accessToken) {
        return flowService.createFlow(flow, accessToken);
    }

    @PostMapping(value = "/edit")
    public Flow editFlow(@RequestBody Flow flow) {
        return flowService.editFlow(flow);
    }

    @GetMapping(value = "/get_all")
    public List<Flow> getAllFlow(@RequestHeader(value = "Authorization") String userToken) {
        return flowService.getAllFlow(userToken);
    }

    @PostMapping(value = "/delete")
    public void deleteFlow(@RequestBody DeleteFlowRequest deleteFlowRequest,
                           @RequestHeader(value = "X-GitHub-Access-Token") String accessToken) {
        flowService.deleteFlow(deleteFlowRequest, accessToken);
    }
}
