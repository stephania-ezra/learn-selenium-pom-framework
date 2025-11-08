package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ScreenshotUtils {
    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    public static Path captureScreenshot(WebDriver driver, String fileName) {
        Path imageFilePath = null;
        if (driver == null) {
            log.error("WebDriver is null! Cannot take screenshot.");
            return null;
        }

        try {
            // 📁 Create dated subfolder to avoid overwriting
            String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Path screenshotDir = Paths.get("reports", "screenshots", dateFolder);
            //Path screenshotDir = Paths.get("test-output", "screenshots", dateFolder);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }
            // 🖼 Capture screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path fromFilePath = srcFile.toPath();
            imageFilePath = screenshotDir.resolve(fileName);

            Files.copy(fromFilePath, imageFilePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("✅ Screenshot captured successfully: {}", imageFilePath);

        } catch (IOException e) {
            log.error("❌ Failed to capture screenshot: {}", e.getMessage());
        }
        return imageFilePath;
    }
}
