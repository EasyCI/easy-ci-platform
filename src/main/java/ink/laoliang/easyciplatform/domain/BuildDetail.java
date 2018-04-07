package ink.laoliang.easyciplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
public class BuildDetail extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private Integer queueNumber;

    @Column(nullable = false)
    private String flowId;

    @Column(nullable = false)
    private String triggerBranch;

    @Column(nullable = false)
    private Boolean isManual;

    @Column(nullable = false)
    private Boolean isBuilding;

    private Boolean isSuccess;

    private String duration;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String version;

    @Column(columnDefinition = "longblob")
    private BuildLog[] buildLogs;

    private String productPreviewUrl;

    public BuildDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getTriggerBranch() {
        return triggerBranch;
    }

    public void setTriggerBranch(String triggerBranch) {
        this.triggerBranch = triggerBranch;
    }

    public Boolean getManual() {
        return isManual;
    }

    public void setManual(Boolean manual) {
        isManual = manual;
    }

    public Boolean getBuilding() {
        return isBuilding;
    }

    public void setBuilding(Boolean building) {
        isBuilding = building;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BuildLog[] getBuildLogs() {
        return buildLogs;
    }

    public void setBuildLogs(BuildLog[] buildLogs) {
        this.buildLogs = buildLogs;
    }

    public String getProductPreviewUrl() {
        return productPreviewUrl;
    }

    public void setProductPreviewUrl(String productPreviewUrl) {
        this.productPreviewUrl = productPreviewUrl;
    }
}
