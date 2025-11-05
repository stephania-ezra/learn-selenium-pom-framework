package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private final Logger log = LogManager.getLogger(LoginTest.class);

    @Test(groups = {"smoke"})
    public void verifyLoginPageLoads() {
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("OrangeHRM"), "Login page not loaded!");
    }

    @Test(groups = {"smoke"})
    public void verifyUserCanLogin() {
        LoginPage loginPage = new LoginPage(driver);
        log.info("username {} password {}", configReader.getUsername(),configReader.getPassword());
        loginPage.login(configReader.getUsername(), configReader.getPassword());
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed!");
    }
}
