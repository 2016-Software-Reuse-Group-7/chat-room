package TeamSeven.util.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValueFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by joshoy on 16/4/18.
 */
public class ConfigManagerImpl extends ConfigManager {

    public ConfigManagerImpl() {
        super();
    }

    public ConfigManagerImpl(String configFile) {
        super(configFile);
    }

    @Override
    public void loadConfig(String configPath) {
        if ( null != configPath ) {
            this.conf = ConfigFactory.load(configPath);
        }
        else {
            // 载入默认配置
            this.conf = ConfigFactory.load();
        }
    }

    /**
     * @param configKey 配置键
     * @return 对应配置键的int值
     */
    @Override
    public int getInt(String configKey) {
        return this.conf.getInt(configKey);
    }

    /**
     * @param configKey 配置键
     * @return 对应配置键的string值
     */
    @Override
    public String getString(String configKey) {
        return this.conf.getString(configKey);
    }

    /**
     * @param configKey 配置键
     * @return 对应配置键的double值
     */
    @Override
    public double getDouble(String configKey) {
        return this.conf.getDouble(configKey);
    }

    /**
     * @param configKey 配置键
     * @return 对应配置键的boolean值
     */
    @Override
    public boolean getBoolean(String configKey) {
        return this.conf.getBoolean(configKey);
    }


    /**
     * @param configKey 配置键
     * @param configValue 配置值
     * @return
     */
    @Override
    public ConfigManager setConfigValue(String configKey, String configValue) {
        // 由于Config对象是Immutable的, 所以要重新赋值
        Config newConf = this.conf.withValue(configKey, ConfigValueFactory.fromAnyRef(configValue));
        this.conf = newConf;
        return this;
    }

    /**
     * @param configKey 配置键
     * @param configValue 配置值
     * @return
     */
    @Override
    public ConfigManager setConfigValue(String configKey, int configValue) {
        // 由于Config对象是Immutable的, 所以要重新赋值
        Config newConf = this.conf.withValue(configKey, ConfigValueFactory.fromAnyRef(configValue));
        this.conf = newConf;
        return this;
    }

    /**
     * @param configKey 配置键
     * @param configValue 配置值
     * @return
     */
    @Override
    public ConfigManager setConfigValue(String configKey, boolean configValue) {
        // 由于Config对象是Immutable的, 所以要重新赋值
        Config newConf = this.conf.withValue(configKey, ConfigValueFactory.fromAnyRef(configValue));
        this.conf = newConf;
        return this;
    }

    /**
     * @param configKey 配置键
     * @param configValue 配置值
     * @return
     */
    @Override
    public ConfigManager setConfigValue(String configKey, double configValue) {
        // 由于Config对象是Immutable的, 所以要重新赋值
        Config newConf = this.conf.withValue(configKey, ConfigValueFactory.fromAnyRef(configValue));
        this.conf = newConf;
        return this;
    }

    /**
     * @param configKey 配置键
     * @param configValue 配置值
     * @return
     */
    @Override
    public ConfigManager setConfigValue(String configKey, Map<String, ? extends Object> configValue) {
        // 由于Config对象是Immutable的, 所以要重新赋值
        Config newConf = this.conf.withValue(configKey, ConfigValueFactory.fromMap(configValue));
        this.conf = newConf;
        return this;
    }

    @Override
    public void saveToFile(File file) throws FileNotFoundException, IOException {
        this.saveToFile(file, ConfigRenderOptions.defaults());
    }

    @Override
    public void saveToFileAsJson(File file) throws FileNotFoundException, IOException {
        this.saveToFile(file, ConfigRenderOptions.concise());
    }

    @Override
    public void saveToFile(File file, ConfigRenderOptions options) throws FileNotFoundException, IOException {
        OutputStream outputStream = null;
        Writer writer = null;
        try {
            String confStr = this.conf.root().render(options);
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            writer.write(confStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            outputStream.close();
        }

    }

}
