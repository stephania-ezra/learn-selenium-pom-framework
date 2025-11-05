package base;

import drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;
    //String env = System.getenv("config.env");
    private final Logger log = LogManager.getLogger(BaseTest.class);
    public ConfigReader configReader = new ConfigReader();
    DriverManager driverManager;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {
        configReader.loadProperties();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        String browser = configReader.getBrowser();
        log.info("browser read from config {}", browser);

        driverManager = new DriverManager(browser);
        driver = driverManager.getDriver();
        driver.get(configReader.getBaseUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driverManager.quitDriver();
    }
}
