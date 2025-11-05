package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    private final Logger log = LogManager.getLogger(ConfigReader.class);
    private final Properties properties = new Properties();


    public void loadProperties() {

        Path path = Paths.get("src", "test", "resources", "config.properties");

        try {
            properties.load(Files.newInputStream(path));
//            properties.forEach((k, v) -> log.info("{} = {}", k, v));
        } catch (IOException e) {
            log.info("unable to read config file {}", e.getMessage());
        }
    }

//    public String getBrowser(String env) {
//        if (env.equalsIgnoreCase("prod"))
//            return properties.getProperty("prod.browser");
//        if (env.equalsIgnoreCase("qa"))
//            return properties.getProperty("qa.browser");
//    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

//    public Properties getProperties() {
//        return properties;
//    }
}

 /*
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }


    public static String get(String key) {
        return props.getProperty(key);
    }*/