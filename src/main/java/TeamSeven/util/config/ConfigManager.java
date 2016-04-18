package TeamSeven.util.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigRenderOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class ConfigManager {

    protected Config conf;

    public ConfigManager() {
        loadConfig(null);
    }

    public ConfigManager(String configFilePath) {
        loadConfig(configFilePath);
    }

    public abstract void loadConfig(String configFilePath);

    public abstract int getInt(String configKey);

    public abstract String getString(String configKey);

    public abstract double getDouble(String configKey);

    public abstract boolean getBoolean(String configKey);

    public abstract ConfigManager setConfigValue(String configKey, String configValue);

    public abstract ConfigManager setConfigValue(String configKey, int configValue);

    public abstract ConfigManager setConfigValue(String configKey, boolean configValue);

    public abstract ConfigManager setConfigValue(String configKey, double configValue);

    public abstract ConfigManager setConfigValue(String configKey, Map<String, ? extends Object> configValue);

    public abstract void saveToFile(File file) throws FileNotFoundException, IOException;

    public abstract void saveToFileAsJson(File file) throws FileNotFoundException, IOException;

    public abstract void saveToFile(File file, ConfigRenderOptions options) throws FileNotFoundException, IOException;
}
