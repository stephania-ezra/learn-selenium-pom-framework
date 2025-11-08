package core;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ReportManager;

import static utils.ReportManager.extentTest;

@Listeners({utils.TestListener.class})
public class LoginTest extends BaseTest {
    private final Logger log = LogManager.getLogger(LoginTest.class);


    @Test(testName = "verifyLoginPageLoads", groups = {"smoke"}, priority = 1)
    public void verifyLoginPageLoads() {
        extentTest = ReportManager.createTest("Verify Login Page Loads");
        String title = driver.getTitle();
        extentTest.info("Page title is: " + title);
        Assert.assertTrue(title.contains("OrangeHRM"), "Login page not loaded!");
        extentTest.pass("Login page loaded successfully");
    }

    @Test(testName = "verifyUserCanLogin", groups = {"smoke"}, priority = 2)
    public void verifyUserCanLogin() {

        extentTest = ReportManager.createTest("Verify User Can Login");
        LoginPage loginPage = new LoginPage(driver);
        log.info("username {} password {}", configReader.getUsername(), configReader.getPassword());

        stopTheFlow(10);
        loginPage.login(configReader.getUsername(), configReader.getPassword());
        extentTest.info("Entered username and password");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed!");
        extentTest.pass("User logged in successfully");
    }

    @Test(testName = "checkInvalidLogin", groups = {"regression"}, priority = 3)
    public void checkInvalidLogin() {

        extentTest = ReportManager.createTest("Check Invalid Login");
        extentTest.info("Entered username: " + configReader.getUsername());
        extentTest.info("Entered password: " + configReader.getPassword().concat("xyz"));
        LoginPage loginPage = new LoginPage(driver);
        stopTheFlow(10);
        loginPage.login(configReader.getUsername(), configReader.getPassword().concat("xyz"));

        stopTheFlow(10);
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.equalsIgnoreCase("Wrong credentials")
                , "Your email or password is incorrect!");
        extentTest.info("Login failed correctly");
        log.info("Login failed correctly");
    }

//    @Test(testName = "verifyLoginPageLoads1", groups = {"smoke"})
//    public void verifyLoginPageLoads1() {
//        Assert.assertTrue(false,"Intentional fail to check screenshot");
//    }
//    //to solve error
//    @Test(testName = "forceFailureTest", groups = {"regression"})
//    public void forceFailureTest() {
//        Assert.fail("Intentional failure to test screenshot capture");
//    }
}
