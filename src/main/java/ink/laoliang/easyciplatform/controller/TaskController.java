package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.domain.request.GithubHookRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @PostMapping(value = "/trigger/{id}")
    public String execute(@PathVariable String id,
                          @RequestBody GithubHookRequest githubHookRequest) {

        System.out.println("webhook 收到了！" + id);
        if (githubHookRequest.getRef() == null) {
            System.out.println("这是首次创建 webhook");
        } else {
            System.out.println("这不是首次创建 webhook！！！" + githubHookRequest.getRef().split("/")[2]);
        }
        return "webhook 收到！";
    }
}
