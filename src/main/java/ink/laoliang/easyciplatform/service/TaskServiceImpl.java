package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.request.GithubHookRequest;
import ink.laoliang.easyciplatform.domain.response.CommonOkResponse;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public CommonOkResponse trigger(String flowId, GithubHookRequest githubHookRequest) {
        String triggerBranch = null;

        if (githubHookRequest.getManual()) {
            // 手动触发的
            triggerBranch = githubHookRequest.getTriggerBranch();
            // 创建一个线程执行构建任务
            // execute(flowId, triggerBranch);
            System.out.println("=====================================");
            System.out.println("开始执行 Flow：" + flowId);
            System.out.println("执行的分支是：" + triggerBranch);
            System.out.println("=====================================");
            return new CommonOkResponse();
        }

        if (githubHookRequest.getRef() != null) {
            // GitHub WebHook 触发的
            triggerBranch = githubHookRequest.getRef().split("/")[2];
            // 创建一个线程执行构建任务
            // execute(flowId, triggerBranch);
            System.out.println("=====================================");
            System.out.println("开始执行 Flow：" + flowId);
            System.out.println("执行的分支是：" + triggerBranch);
            System.out.println("=====================================");
            return new CommonOkResponse();
        }

        // 首次创建 Flow，GitHub 的 WebHook 测试回调
        return new CommonOkResponse();
    }

    /**
     * 执行具体的构建任务
     *
     * @param flowId
     * @param triggerBranch
     */
    private void execute(String flowId, String triggerBranch) {

    }
}
