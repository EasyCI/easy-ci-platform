package ink.laoliang.easyciplatform.domain;

public class FlowDetail {

    private Flow flow;

    private Integer historySum;

    private Integer successSum;

    private Integer failureSum;

    private Double rating;

    private String lastBuildTime;

    public FlowDetail(Flow flow, Integer historySum, Integer successSum, Integer failureSum, Double rating, String lastBuildTime) {
        this.flow = flow;
        this.historySum = historySum;
        this.successSum = successSum;
        this.failureSum = failureSum;
        this.rating = rating;
        this.lastBuildTime = lastBuildTime;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public Integer getHistorySum() {
        return historySum;
    }

    public void setHistorySum(Integer historySum) {
        this.historySum = historySum;
    }

    public Integer getSuccessSum() {
        return successSum;
    }

    public void setSuccessSum(Integer successSum) {
        this.successSum = successSum;
    }

    public Integer getFailureSum() {
        return failureSum;
    }

    public void setFailureSum(Integer failureSum) {
        this.failureSum = failureSum;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLastBuildTime() {
        return lastBuildTime;
    }

    public void setLastBuildTime(String lastBuildTime) {
        this.lastBuildTime = lastBuildTime;
    }
}
