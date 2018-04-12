package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.*;
import ink.laoliang.easyciplatform.domain.request.GithubHookRequest;
import ink.laoliang.easyciplatform.domain.response.BuildDetailResponse;
import ink.laoliang.easyciplatform.domain.response.CommonOkResponse;
import ink.laoliang.easyciplatform.repository.BuildDetailRepository;
import ink.laoliang.easyciplatform.repository.FlowRepository;
import ink.laoliang.easyciplatform.repository.GithubRepoRepository;
import ink.laoliang.easyciplatform.repository.PluginRepository;
import ink.laoliang.easyciplatform.util.CustomConfigration;
import ink.laoliang.easyciplatform.util.FormatDuration;
import ink.laoliang.easyciplatform.util.MD5EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    final static String SUCCESS_STATUS = "SUCCESS";
    final static String FAILED_STATUS = "FAILED";

    @Autowired
    private BuildDetailRepository buildDetailRepository;

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Autowired
    private CustomConfigration customConfigration;

    @Autowired
    private BuildDetailResponse buildDetailResponse;

    @Override
    public CommonOkResponse trigger(String flowId, GithubHookRequest githubHookRequest) {
        String triggerBranch;
        Boolean isManual;

        if (githubHookRequest.getRef() != null) {
            // GitHub WebHook 触发的
            triggerBranch = githubHookRequest.getRef().split("/")[2];
            isManual = false;
            System.out.println("=====================================");
            System.out.println("开始执行 Flow：" + flowId);
            System.out.println("触发执行的分支是：" + triggerBranch);
            System.out.println("是否为手动触发：" + isManual);
            System.out.println("=====================================");
            // 创建一个线程执行构建任务
            new Thread(() -> execute(flowId, triggerBranch, isManual)).start();
            return new CommonOkResponse();
        }

        if (githubHookRequest.getIsManual() != null && githubHookRequest.getIsManual()) {
            // 手动触发的
            triggerBranch = githubHookRequest.getTriggerBranch();
            isManual = true;
            System.out.println("=====================================");
            System.out.println("开始执行 Flow：" + flowId);
            System.out.println("触发执行的分支是：" + triggerBranch);
            System.out.println("是否为手动触发：" + isManual);
            System.out.println("=====================================");
            // 创建一个线程执行构建任务
            new Thread(() -> execute(flowId, triggerBranch, isManual)).start();
            return new CommonOkResponse();
        }

        // 以上条件都不符合，那么就是首次创建 Flow，GitHub 的 WebHook 测试回调
        return new CommonOkResponse();
    }

    @Override
    public BuildDetailResponse upToDate(String flowId) {
        Sort orders = new Sort(Sort.Direction.DESC, "queueNumber");
        List<BuildDetail> buildDetails = buildDetailRepository.findAllByFlowId(flowId, orders);
        buildDetailResponse.setBuildDetails(buildDetails);
        return buildDetailResponse;
    }

    /**
     * 执行具体的构建任务
     *
     * @param flowId
     * @param triggerBranch
     */
    private void execute(String flowId, String triggerBranch, Boolean isManual) {
        long startTime = System.currentTimeMillis();
        Boolean isSuccess = true;

        BuildDetail buildDetail = new BuildDetail();
        Flow flow = flowRepository.findOne(flowId);
        GithubRepo githubRepo = githubRepoRepository.findOne(flow.getRepoId());

        buildDetail.setId(generateTaskId(flowId));
        buildDetail.setQueueNumber(buildDetailRepository.findAllByFlowId(flowId, null).size() + 1);
        buildDetail.setFlowId(flowId);
        buildDetail.setTriggerBranch(triggerBranch);
        buildDetail.setManual(isManual);
        buildDetail.setBuilding(true);
        buildDetail.setPlatform(flow.getPlatform());
        buildDetail.setVersion(flow.getVersion());
        buildDetailRepository.save(buildDetail);

        List<Plugin> pluginList = new ArrayList<>();
        for (String scriptName : flow.getPlugins()) {
            Plugin plugin = pluginRepository.findOne(scriptName);
            List<PluginEnv> pluginEnvs = new ArrayList<>();
            if (plugin.getNeedEnv() != null) {
                for (PluginEnv pluginEnv : plugin.getNeedEnv()) {
                    for (String needEnv : flow.getNeedEnv()) {
                        if (pluginEnv.getEnvName().equals(needEnv.split("===")[0])) {
                            pluginEnv.setEnvValue(needEnv.split("===")[1]);
                            pluginEnvs.add(pluginEnv);
                        }
                    }
                }
            }
            plugin.setNeedEnv(pluginEnvs.toArray(new PluginEnv[pluginEnvs.size()]));
            pluginList.add(plugin);
        }

        // 开始必备两步 (git clone & init env)
        List<BuildLog> buildLogs = new ArrayList<>();
        List<String> scriptParameters = new ArrayList<>();
        scriptParameters.add(flowId);
        scriptParameters.add(buildDetail.getId());
        scriptParameters.add(githubRepo.getName());
        scriptParameters.add(flow.getPlatform());
        scriptParameters.add(githubRepo.getCloneUrl());
        scriptParameters.add(triggerBranch);
        String log;
        log = executeScript("git_clone", scriptParameters.toArray(new String[scriptParameters.size()]));
        System.out.println(log);
        // 更新 Build_Detail 数据表
        BuildLog buildLog = new BuildLog();
        if (log.charAt(log.length() - 1) == '0') {
            // 当前插件运行成功
            buildLog.setStepName("Git Clone");
            buildLog.setComplete(true);
            buildLog.setLogContent(log);
            buildLog.setSuccess(true);
            buildLogs.add(buildLog);
            buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
            buildDetailRepository.save(buildDetail);
        } else {
            // 当前插件运行失败
            buildLog.setStepName("Git Clone");
            buildLog.setComplete(true);
            buildLog.setLogContent(log);
            buildLog.setSuccess(false);
            buildLogs.add(buildLog);
            buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
            buildDetailRepository.save(buildDetail);
            isSuccess = false;
        }
        log = executeScript("init_env", scriptParameters.toArray(new String[scriptParameters.size()]));
        System.out.println(log);
        // 更新 Build_Detail 数据表
        buildLog = new BuildLog();
        if (log.charAt(log.length() - 1) == '0') {
            // 当前插件运行成功
            buildLog.setStepName("初始化环境");
            buildLog.setComplete(true);
            buildLog.setLogContent(log);
            buildLog.setSuccess(true);
            buildLogs.add(buildLog);
            buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
            buildDetailRepository.save(buildDetail);
        } else {
            // 当前插件运行失败
            buildLog.setStepName("初始化环境");
            buildLog.setComplete(true);
            buildLog.setLogContent(log);
            buildLog.setSuccess(false);
            buildLogs.add(buildLog);
            buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
            buildDetailRepository.save(buildDetail);
            isSuccess = false;
        }

        // 依次跑其余自定义脚本插件
        for (Plugin plugin : pluginList) {
            scriptParameters = new ArrayList<>();
            scriptParameters.add(flowId);
            scriptParameters.add(buildDetail.getId());
            scriptParameters.add(githubRepo.getName());
            scriptParameters.add(flow.getPlatform());
            for (PluginEnv pluginEnv : plugin.getNeedEnv()) {
                scriptParameters.add(pluginEnv.getEnvValue());
            }
            // 如果这是最后一步——“邮件通知”，那么将会检测当前是否有产物输出，如果有，将会作为邮件内容添加到参数列表末尾用于构建邮件通知
            if (plugin.getScriptName().equals("send_email")) {
                String productPreviewUrl = buildDetail.getProductPreviewUrl();
                if (productPreviewUrl != null) {
                    scriptParameters.add(productPreviewUrl);
                }
            }
            log = executeScript(plugin.getScriptName(), scriptParameters.toArray(new String[scriptParameters.size()]));
            System.out.println(log);
            // 更新 Build_Detail 数据表
            buildLog = new BuildLog();
            if (log.charAt(log.length() - 1) == '0') {
                // 当前插件运行成功
                buildLog.setStepName(plugin.getFullName());
                buildLog.setComplete(true);
                buildLog.setLogContent(log);
                buildLog.setSuccess(true);
                buildLogs.add(buildLog);
                buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
                // 如果当前步骤有产物出现，将产物也更新到 Build_Detail 数据表中
                if (plugin.getScriptName().equals("fir_upload")) {
                    String previewUrl = log.split("\n")[1].split(" ")[2];
                    buildDetail.setProductPreviewUrl(previewUrl);
                }
                buildDetailRepository.save(buildDetail);
            } else {
                // 当前插件运行失败
                buildLog.setStepName(plugin.getFullName());
                buildLog.setComplete(true);
                buildLog.setLogContent(log);
                buildLog.setSuccess(false);
                buildLogs.add(buildLog);
                buildDetail.setBuildLogs(buildLogs.toArray(new BuildLog[buildLogs.size()]));
                buildDetailRepository.save(buildDetail);
                isSuccess = false;
            }
        }

        // 最后执行清理脚本
        executeScript("clean", new String[]{flowId, buildDetail.getId()});

        // 更新构建状态
        buildDetail.setBuilding(false);
        // 更新构建结果
        buildDetail.setSuccess(isSuccess);
        // 更新用时
        long endTime = System.currentTimeMillis();
        buildDetail.setDuration(FormatDuration.byMillisecond(endTime - startTime));
        buildDetailRepository.save(buildDetail);
    }

    /**
     * 使用 flowId 及 系统当前时间生成唯一的 taskId
     *
     * @param flowId
     * @return
     */
    private String generateTaskId(String flowId) {
        return MD5EncodeUtil.encode(flowId + System.currentTimeMillis());
    }

    private String executeScript(String scriptName, String[] scriptParameters) {
        // 拼接脚本文件名称
        String pythonScript = customConfigration.getPluginScriptPath() + scriptName + ".py";

        // 拼接执行脚本语句
        List<String> executeStatement = new ArrayList<>();
        executeStatement.add("python3");
        executeStatement.add(pythonScript);
        if (scriptParameters != null) {
            for (String parameter : scriptParameters) {
                executeStatement.add(parameter);
            }
        }

        Process process;
        StringBuilder logBuilder = new StringBuilder();
        String stepStatus = "UNKNOWN";
        int scriptExitValue = -1;
        long startTime = 0;
        long endTime = 0;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(executeStatement);
            processBuilder.redirectErrorStream(true);

            // 获取开始时间
            startTime = System.currentTimeMillis();
            // 开始执行脚本
            process = processBuilder.start();
            // 等待脚本执行结束并返回
            process.waitFor();
            // 获取结束时间
            endTime = System.currentTimeMillis();

            // 读取脚本输出日志
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            while ((line = input.readLine()) != null) {
                logBuilder.append("\n").append(line);
            }
            input.close();

            if (process.exitValue() == 0) {
                // Python 环境正常，脚本正常启动
                // 继续读取 Python 脚本退出码，判断脚本执行情况
                scriptExitValue = Integer.parseInt(logBuilder.substring(logBuilder.lastIndexOf("\n") + 1, logBuilder.length()));
                if (scriptExitValue == 0) {
                    // Python 脚本退出码为 0，脚本执行成功（即null任务成功）
                    stepStatus = SUCCESS_STATUS;
                } else {
                    // Python 脚本退出码不为 0，脚本执行失败
                    stepStatus = FAILED_STATUS;
                }
            } else {
                stepStatus = FAILED_STATUS;
                // 为最后格式化日志输出匹配规则，这里在末尾追加一个"\n"
                logBuilder.append("\n");
            }

        } catch (IOException e) {
            logBuilder.append("Cannot run program python: CreateProcess error=2, 系统找不到指定的文件。\n");
        } catch (InterruptedException e) {
            logBuilder.append(e.getMessage() + "\n");
        }

        return logBuilder.toString().substring(0, logBuilder.lastIndexOf("\n")) + "\n" +
                "[EasyCI] ------------------------------------------------------------------------\n" +
                "[EasyCI] " + scriptName.toUpperCase() + " " + stepStatus + "\n" +
                "[EasyCI] ------------------------------------------------------------------------\n" +
                "[EasyCI] Duration: " + FormatDuration.byMillisecond(endTime - startTime) + "\n" +
                "[EasyCI] Finished at: " + new Date(endTime) + "\n" +
                "[EasyCI] ------------------------------------------------------------------------\n" +
                "[EasyCI] Exit: " + scriptExitValue; // 最后的 Python 脚本退出码用于前端判断任务执行状态
    }
}
