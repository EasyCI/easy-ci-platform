package ink.laoliang.easyciplatform.domain.response;

public class CommonOkResponse {

    private Integer code = 200;

    private String status = "OK";

    public CommonOkResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
