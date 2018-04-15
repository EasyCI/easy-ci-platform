package ink.laoliang.easyciplatform.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PluginEnv implements Serializable {

    private String envName;

    private String envValue;

    private String envDescription;

    public PluginEnv() {
    }

    public PluginEnv(String envName, String envValue, String envDescription) {
        this.envName = envName;
        this.envValue = envValue;
        this.envDescription = envDescription;
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

    public String getEnvDescription() {
        return envDescription;
    }

    public void setEvnDescription(String envDescription) {
        this.envDescription = envDescription;
    }
}
