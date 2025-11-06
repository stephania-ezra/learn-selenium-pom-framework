package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class LoginPage {

    WebDriver driver;
    private final Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(xpath="//input[@placeholder='Username']")
    private WebElement usernameField;

    @FindBy(xpath="//input[@placeholder='Password']")
    private WebElement passwordField;

    @FindBy(xpath="//button[normalize-space()='Login']")
    private WebElement loginButton;

    @FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        if(driver == null){
            throw new IllegalArgumentException("WebDriver instance is null! Please initialize the driver " +
                    "before creating the page object.");
        }
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        WaitUtils.waitForVisible(driver, usernameField);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }

}
