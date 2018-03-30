package ink.laoliang.easyciplatform.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
public class Plugin {

    @Id
    private String scriptName;

    @Column(nullable = false)
    private String fullName;

    private String description;

    private String[] needEnv;

    public Plugin() {
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getNeedEnv() {
        return needEnv;
    }

    public void setNeedEnv(String[] needEnv) {
        this.needEnv = needEnv;
    }
}
