package si.fri.rso.catalogue.cdi.configuration;


import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@ConfigBundle("rest-config")
public class ConfigProperties {

    @ConfigValue(watch = true)
    private String stringProperty;

    private Boolean booleanProperty;

    private Integer integerProperty;

    @ConfigValue(watch = true)
    private String encodedProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public Boolean getBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(Boolean booleanPropertysomeBoolean) {
        this.booleanProperty = booleanPropertysomeBoolean;
    }

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(Integer integerProperty) {
        this.integerProperty = integerProperty;
    }

    public String getEncodedProperty() {
        return encodedProperty;
    }

    public void setEncodedProperty(String encodedProperty) {
        this.encodedProperty = encodedProperty;
    }
}