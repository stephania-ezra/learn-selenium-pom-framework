package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends ScreenshotUtils implements ITestListener {

    public TestListener() {
        super();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot(result.getTestName()+".jpg");
    }
}
