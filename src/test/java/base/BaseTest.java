package base;

import drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ReportManager;

import java.time.Duration;

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

    //@BeforeClass(alwaysRun = true)
    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {
        String browser = configReader.getBrowser();
        log.info("browser read from config {}", browser);

        driverManager = new DriverManager(browser);
        driver = DriverManager.getDriver();
        context.setAttribute("WebDriver", driver);
        driver.get(configReader.getBaseUrl());
    }

    //@Test
    //stop the flow
    public void stopTheFlow(int value) {
        try {
            if (value == 0)
                value = 10;
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

    //@AfterClass(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        stopTheFlow(10);
        driverManager.quitDriver();
    }
}
