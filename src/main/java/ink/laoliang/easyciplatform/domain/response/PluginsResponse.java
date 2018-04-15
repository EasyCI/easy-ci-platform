package ink.laoliang.easyciplatform.domain.response;

import ink.laoliang.easyciplatform.domain.Plugin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PluginsResponse {

    private List<Plugin> plugins;

    public PluginsResponse() {
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }
}
