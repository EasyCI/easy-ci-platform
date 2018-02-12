package ink.laoliang.easyciplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
public class Flow extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Long repoId;

    @Column(nullable = false)
    private Long hookId;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String version;

    private String[] triggerPush;

    private String[] plugins;

    private String[] inputs;

    public Flow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    public Long getHookId() {
        return hookId;
    }

    public void setHookId(Long hookId) {
        this.hookId = hookId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getTriggerPush() {
        return triggerPush;
    }

    public void setTriggerPush(String[] triggerPush) {
        this.triggerPush = triggerPush;
    }

    public String[] getPlugins() {
        return plugins;
    }

    public void setPlugins(String[] plugins) {
        this.plugins = plugins;
    }

    public String[] getInputs() {
        return inputs;
    }

    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }
}
