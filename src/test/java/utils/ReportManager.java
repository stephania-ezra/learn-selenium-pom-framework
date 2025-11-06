package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extent;
    public static ExtentTest test;

    public static ExtentReports getReporter() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/AutomationReport_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Selenium Automation Report");
            spark.config().setReportName("Smoke Test Results");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getReporter().createTest(testName);
        return test;
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
