package si.fri.rso.catalogue.cdi.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;

import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
@ConfigBundle("kumuluzee")

//environments/dev/services/catalogue-service/1.0.0/config/kumuluzee/server/base-url

public class KumuluzeeConfig {
    private String name;
    private String env;
    private Server server;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}

