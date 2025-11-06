package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils{
    private WebDriver driver;

    public ScreenshotUtils() {
    }

    public String captureScreenshot(String testName) {

        if(driver==null){
            System.err.println(" WebDriver is null! Cannot take screenshot.");
            return null;
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = "test-output/screenshots/" + testName + "_" + timestamp + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }
        return timestamp;
    }
}
