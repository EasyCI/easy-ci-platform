package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.Plugin;
import ink.laoliang.easyciplatform.domain.request.FlowDeleteRequest;
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
    public List<Plugin> getPlugins() {
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
    public List<Flow> getAllFlow() {
        return flowService.getAllFlow();
    }

    @PostMapping(value = "/delete")
    public void deleteFlow(@RequestBody FlowDeleteRequest flowDeleteRequest,
                           @RequestHeader(value = "X-GitHub-Access-Token") String accessToken) {
        flowService.deleteFlow(flowDeleteRequest, accessToken);
    }
}
