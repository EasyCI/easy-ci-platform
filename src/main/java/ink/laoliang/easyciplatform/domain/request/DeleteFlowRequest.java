package ink.laoliang.easyciplatform.domain.request;

public class DeleteFlowRequest {

    private String flowId;

    private Integer hookId;

    private Long repoId;

    public DeleteFlowRequest(){
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Integer getHookId() {
        return hookId;
    }

    public void setHookId(Integer hookId) {
        this.hookId = hookId;
    }

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }
}
