package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.Flow;
import ink.laoliang.easyciplatform.domain.GithubRepo;
import ink.laoliang.easyciplatform.domain.Plugin;
import ink.laoliang.easyciplatform.domain.repository.FlowRepository;
import ink.laoliang.easyciplatform.domain.repository.GithubRepoRepository;
import ink.laoliang.easyciplatform.domain.repository.PluginRepository;
import ink.laoliang.easyciplatform.domain.request.FlowDeleteRequest;
import ink.laoliang.easyciplatform.exception.GithubHookException;
import ink.laoliang.easyciplatform.util.MD5EncodeUtil;
import org.eclipse.egit.github.core.RepositoryHook;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private GithubRepo githubRepo;

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Override
    public List<Plugin> getPlugins() {
        return pluginRepository.findAll();
    }

    @Override
    public Flow createFlow(Flow flow, String accessToken) {
        // 生成 flow 的唯一 id
        String id = generateFlowId(flow);

        // 创建 webhook
        RepositoryHook repositoryHook = new RepositoryHook();
        Map<String, String> hookConfig = new HashMap<>();
        hookConfig.put("events", "push");
        hookConfig.put("url", "http://ca1eaef2.ngrok.io/task/execute/" + id);
        hookConfig.put("content_type", "json");
        repositoryHook.setConfig(hookConfig);
        repositoryHook.setName("web");
        repositoryHook.setActive(true);

        // 为远程仓库添加 webhook
        GitHubClient gitHubClient = new GitHubClient().setOAuth2Token(accessToken);
        RepositoryService repositoryService = new RepositoryService(gitHubClient);
        try {
            githubRepo = githubRepoRepository.findById(flow.getRepoId());
            repositoryHook = repositoryService.createHook(repositoryService.getRepository(githubRepo.getLogin(), githubRepo.getName()), repositoryHook);
        } catch (IOException e) {
            throw new GithubHookException(e.getMessage());
        }

        // flow存库
        flow.setId(id);
        flow.setHookId(repositoryHook.getId());
        return flowRepository.save(flow);
    }

    @Override
    public Flow editFlow(Flow flow) {
        // 生成 flow 的唯一 id
        String id = generateFlowId(flow);
        flow.setId(id);
        return flowRepository.save(flow);
    }

    @Override
    public List<Flow> getAllFlow() {
        return flowRepository.findAll();
    }

    @Override
    public void deleteFlow(FlowDeleteRequest flowDeleteRequest, String accessToken) {
        // 删除远程仓库 WebHook
        GitHubClient gitHubClient = new GitHubClient().setOAuth2Token(accessToken);
        RepositoryService repositoryService = new RepositoryService(gitHubClient);
        githubRepo = githubRepoRepository.findById(flowDeleteRequest.getRepoId());
        try {
            repositoryService.deleteHook(repositoryService.getRepository(githubRepo.getLogin(), githubRepo.getName()), flowDeleteRequest.getHookId());
        } catch (IOException e) {
            throw new GithubHookException(e.getMessage());
        }

        // 删库
        flowRepository.delete(flowDeleteRequest.getFlowId());
    }

    /**
     * 生成 Flow 的唯一 Id
     *
     * @param flow
     * @return
     */
    private String generateFlowId(Flow flow) {
        githubRepo = githubRepoRepository.findById(flow.getRepoId());
        return MD5EncodeUtil.encode(flow.getName()
                + flow.getUserEmail()
                + githubRepo.getLogin()
                + githubRepo.getName());
    }
}
