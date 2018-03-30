package ink.laoliang.easyciplatform.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PluginEnv implements Serializable {

    private String envName;

    private String envValue;

    public PluginEnv() {
    }

    public PluginEnv(String envName, String envValue) {
        this.envName = envName;
        this.envValue = envValue;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnvValue() {
        return envValue;
    }

    public void setEnvValue(String envValue) {
        this.envValue = envValue;
    }
}
