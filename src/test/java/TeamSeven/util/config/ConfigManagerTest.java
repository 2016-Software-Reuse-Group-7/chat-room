package TeamSeven.util.config;

import org.junit.Test;

import java.util.Random;


/**
 * Created by joshoy on 16/4/18.
 */
public class ConfigManagerTest {

    @Test
    public void testConfigManager() throws Exception {

        ConfigManager cm = new ConfigManagerImpl("testConfig");
        System.out.println("Read param 'port' as int: " + cm.getInt("server.port"));
        System.out.println("Read param 'heartBeatActivated' as boolean: " + cm.getBoolean("server.heartBeatActivated"));
        System.out.println("Read param 'welcomeWord' as String: " + cm.getString("server.welcomeWord"));

    }

    @Test
    public void testConfigManagerWrite() throws Exception {

        ConfigManager cm = new ConfigManagerImpl("testConfig");
        System.out.println("Before config magic number is: " + cm.getInt("server.magicNumber"));

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);

        cm.setConfigValue("server.magicNumber", randomInt);
        System.out.println("After config magic number is: " + cm.getInt("server.magicNumber"));

    }

}