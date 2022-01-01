package si.fri.rso.catalogue.cdi.configuration;


import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@ConfigBundle("rest-config")
public class ConfigProperties {

    @ConfigValue(watch = true)
    private String externalApiKey;

    public String getExternalApiKey() {
        return externalApiKey;
    }

    public void setExternalApiKey(String externalApiKey) {
        this.externalApiKey = externalApiKey;
    }
}