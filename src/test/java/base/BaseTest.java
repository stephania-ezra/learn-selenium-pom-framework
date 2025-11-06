package base;

import drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ReportManager;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
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

    //@Test
    //stop the flow
    public void stopTheFlow(int value) {
        try {
            if (value == 0)
                value = 20;
            log.info("waiting {} seconds", value);
            Thread.sleep(Duration.ofSeconds(value));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ReportManager.flushReports();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driverManager.quitDriver();
    }
}
