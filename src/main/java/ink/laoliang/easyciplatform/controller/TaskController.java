package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.request.GithubHookRequest;
import ink.laoliang.easyciplatform.domain.response.CommonOkResponse;
import ink.laoliang.easyciplatform.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/trigger/{flowId}")
    public CommonOkResponse trigger(@PathVariable String flowId,
                                    @RequestBody GithubHookRequest githubHookRequest) {
        return taskService.trigger(flowId, githubHookRequest);
    }
}
