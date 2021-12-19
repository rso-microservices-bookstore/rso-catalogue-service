package si.fri.rso.catalogue.cdi.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigValue;

public class ServerConfig {
    private String baseUrl;
    @ConfigValue("http.port")
    private Integer port;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    // getter and setter methods
}
