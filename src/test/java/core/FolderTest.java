package core;

import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FolderTest {

    @Test
    void test_path() {
        // reports/screenshots/08-11-2025/LoginTest-checkInvalidLogin.png
        Path reportsFolder = Paths.get("reports", "screenshots", "10-11-2025"
                , "LoginTest-checkInvalidLogin.png");
        //Path screenshotFolder = reportsFolder.resolve("screenshots", "10-11-2025");

        System.out.println("screenshotFolder {} " + reportsFolder);
        System.out.println("screenshotFolder {} " + reportsFolder.toString()
                .replaceAll("reports/", ""));
    }
}
