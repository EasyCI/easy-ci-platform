package ink.laoliang.easyciplatform.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
public class CustomConfigration {

    private String serverHost;

    private String githubClientId;

    private String githubClientSecret;

    private String githubAuthorizationScopes;

    private String pluginScriptPath;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getGithubClientId() {
        return githubClientId;
    }

    public void setGithubClientId(String githubClientId) {
        this.githubClientId = githubClientId;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }

    public void setGithubClientSecret(String githubClientSecret) {
        this.githubClientSecret = githubClientSecret;
    }

    public String getGithubAuthorizationScopes() {
        return githubAuthorizationScopes;
    }

    public void setGithubAuthorizationScopes(String authorizationScopes) {
        this.githubAuthorizationScopes = authorizationScopes;
    }

    public String getPluginScriptPath() {
        return pluginScriptPath;
    }

    public void setPluginScriptPath(String pluginScriptPath) {
        this.pluginScriptPath = pluginScriptPath;
    }
}
