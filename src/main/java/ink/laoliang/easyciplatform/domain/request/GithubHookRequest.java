package ink.laoliang.easyciplatform.domain.request;

public class GithubHookRequest {

    /**
     * 对应 GitHub 的 WebHook 回调响应字段，
     * 可以从中截取处触发分支
     */
    private String ref;

    /**
     * Github 回调时，该字段为 null；
     * 手动触发构建时，该字段为 true；
     */
    private Boolean isManual;

    /**
     * Github 回调时，该字段为 null
     */
    private String triggerBranch;

    public GithubHookRequest() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean getIsManual() {
        return isManual;
    }

    public void setIsManual(Boolean manual) {
        isManual = manual;
    }

    public String getTriggerBranch() {
        return triggerBranch;
    }

    public void setTriggerBranch(String triggerBranch) {
        this.triggerBranch = triggerBranch;
    }
}
