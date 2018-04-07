package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.request.GithubHookRequest;
import ink.laoliang.easyciplatform.domain.response.BuildDetailResponse;
import ink.laoliang.easyciplatform.domain.response.CommonOkResponse;

public interface TaskService {
    CommonOkResponse trigger(String flowId, GithubHookRequest githubHookRequest);

    BuildDetailResponse upToDate(String flowId);
}
