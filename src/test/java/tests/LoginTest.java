package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ReportManager;

import static utils.ReportManager.test;

public class LoginTest extends BaseTest {
    private final Logger log = LogManager.getLogger(LoginTest.class);
    public BaseTest baseTest = new BaseTest();

    @Test(testName = "verifyLoginPageLoads", groups = {"smoke"}, priority = 1)
    public void verifyLoginPageLoads() {
        test = ReportManager.createTest("Verify Login Page Loads");
        String title = driver.getTitle();
        test.info("Page title is: " + title);
        Assert.assertTrue(title.contains("OrangeHRM"), "Login page not loaded!");
        test.pass("Login page loaded successfully");
    }

    @Test(testName = "verifyUserCanLogin", groups = {"smoke"}, priority = 2)
    public void verifyUserCanLogin() {
        test = ReportManager.createTest("Verify User Can Login");
        LoginPage loginPage = new LoginPage(driver);
        log.info("username {} password {}", configReader.getUsername(), configReader.getPassword());
        loginPage.login(configReader.getUsername(), configReader.getPassword());
        test.info("Entered username and password");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed!");
        test.pass("User logged in successfully");
    }

    @Test(testName = "checkInvalidLogin", groups = {"regression"}, priority = 3)
    public void checkInvalidLogin() {
        test = ReportManager.createTest("Check Invalid Login");
        test.info("Entered username: " + configReader.getUsername());
        test.info("Entered password: " + configReader.getPassword().concat("xyz"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(configReader.getUsername(), configReader.getPassword().concat("xyz"));

        baseTest.stopTheFlow(10);
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.equalsIgnoreCase("Invalid credentials")
                , "Your email or password is incorrect!");
        test.info("Login failed correctly");
        log.info("Login failed correctly");
    }
}
