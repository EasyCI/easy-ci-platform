package ink.laoliang.easyciplatform.domain;

import java.io.Serializable;

public class BuildLog implements Serializable {

    private String stepName;

    private Boolean isComplete;

    private String logContent;

    private Boolean isSuccess;

    public BuildLog() {
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
