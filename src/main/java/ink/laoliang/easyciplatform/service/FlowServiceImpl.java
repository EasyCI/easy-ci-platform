package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.*;
import ink.laoliang.easyciplatform.domain.request.DeleteFlowRequest;
import ink.laoliang.easyciplatform.domain.response.PluginsResponse;
import ink.laoliang.easyciplatform.exception.GithubHookException;
import ink.laoliang.easyciplatform.repository.FlowRepository;
import ink.laoliang.easyciplatform.repository.GithubRepoRepository;
import ink.laoliang.easyciplatform.repository.PluginRepository;
import ink.laoliang.easyciplatform.repository.UserRepository;
import ink.laoliang.easyciplatform.util.CustomConfigration;
import ink.laoliang.easyciplatform.util.MD5EncodeUtil;
import ink.laoliang.easyciplatform.util.UserTokenByJwt;
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

    @Autowired
    private CustomConfigration customConfigration;

    @Autowired
    private PluginsResponse pluginsResponse;

    @Autowired
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PluginsResponse getPlugins() {
        // 每次都更新初始化一下插件列表
        initPluginList();
        pluginsResponse.setPlugins(pluginRepository.findAll());
        return pluginsResponse;
    }

    @Override
    public Flow createFlow(Flow flow, String accessToken) {
        // 生成 flow 的唯一 id
        String id = generateFlowId(flow);

        // 创建 webhook
        RepositoryHook repositoryHook = new RepositoryHook();
        Map<String, String> hookConfig = new HashMap<>();
        hookConfig.put("events", "push");
        hookConfig.put("url", customConfigration.getServerHost() + "/task/trigger/" + id);
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
    public List<Flow> getAllFlow(String userToken) {
        user = UserTokenByJwt.parserToken(userToken, userRepository);
        return flowRepository.findAllByUserEmail(user.getEmail());
    }

    @Override
    public void deleteFlow(DeleteFlowRequest deleteFlowRequest, String accessToken) {
        // 删除远程仓库 WebHook
        GitHubClient gitHubClient = new GitHubClient().setOAuth2Token(accessToken);
        RepositoryService repositoryService = new RepositoryService(gitHubClient);
        githubRepo = githubRepoRepository.findById(deleteFlowRequest.getRepoId());
        try {
            repositoryService.deleteHook(repositoryService.getRepository(githubRepo.getLogin(), githubRepo.getName()), deleteFlowRequest.getHookId());
        } catch (IOException e) {
            throw new GithubHookException(e.getMessage());
        }

        // 删库
        flowRepository.delete(deleteFlowRequest.getFlowId());
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

    /**
     * 系统每次添加新插件，就在这里添加新的一组
     * 有空再把它提取到配置文件中
     */
    private void initPluginList() {
        Plugin plugin;
        PluginEnv[] needEnv;

        plugin = new Plugin();
        plugin.setScriptName("unit_test");
        plugin.setFullName("单元测试");
        plugin.setDescription("用于构建项目单元测试");
        needEnv = null;
        plugin.setNeedEnv(needEnv);
        pluginRepository.save(plugin);

        plugin = new Plugin();
        plugin.setScriptName("build_apk");
        plugin.setFullName("Android 打包");
        plugin.setDescription("编译生成 Android 项目的安装包");
        needEnv = null;
        plugin.setNeedEnv(needEnv);
        pluginRepository.save(plugin);

        plugin = new Plugin();
        plugin.setScriptName("fir_upload");
        plugin.setFullName("上传 fir");
        plugin.setDescription("将打包产物上传到 fir.im 托管平台");
        needEnv = new PluginEnv[]{
                new PluginEnv("FIR_API_TOKEN", null, "【必填项】fir.im 的 API Token，可从您 fir.im 账户管理中获取"),
                new PluginEnv("FIR_CHANGELOG", null, "【选填项】为 fir.im 应用托管更新自定义日志")
        };
        plugin.setNeedEnv(needEnv);
        pluginRepository.save(plugin);

        plugin = new Plugin();
        plugin.setScriptName("send_email");
        plugin.setFullName("邮件通知");
        plugin.setDescription("将构建结果发送到指定的邮箱");
        needEnv = new PluginEnv[]{
                new PluginEnv("EMAIL_ADDRESS", null, "【必填项】邮件通知插件的邮件接收地址")
        };
        plugin.setNeedEnv(needEnv);
        pluginRepository.save(plugin);
    }
}
