package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Path;

public class TestListener extends ReportManager implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("❌ Test failed: {}.{}",
                result.getTestClass().getName(),
                result.getMethod().getMethodName());

        ITestContext testContext = result.getTestContext();
        WebDriver driver = (WebDriver) testContext.getAttribute("WebDriver");

        if (driver != null) {
            String fileName = result.getTestClass()
                    .getRealClass()
                    .getSimpleName()
                    + "-" + result.getName() + ".png";

            Path imageFilePath = ScreenshotUtils.captureScreenshot(driver, fileName);

            // extent reports
            if (imageFilePath != null) {
                String screenShotRelativePath = imageFilePath.toString().replaceAll("reports/", "");

                extentTest.fail(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
                extentTest.fail(result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenShotRelativePath).build());
            }
        } else {
            log.error("⚠️ WebDriver is null in onTestFailure — screenshot not captured!");
        }

        // logs
        log.info("Listener triggered for test: {}", result.getName());
        log.info("WebDriver: {}", driver);
    }
}
