package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extentReports;
    public static ExtentTest extentTest;

    public static ExtentReports getReporter() {
        if (extentReports == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/AutomationReport_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Selenium Automation Report");
            spark.config().setReportName("Smoke Test Results");
            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
        }
        return extentReports;
    }

    public static ExtentTest createTest(String testName) {
        extentTest = getReporter().createTest(testName);
        return extentTest;
    }

//    @AfterMethod(alwaysRun = true)
//    public void getResult(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            extentTest.fail(result.getThrowable());
//        } else if (result.getStatus() == ITestResult.SUCCESS) {
//            extentTest.pass("Test passed successfully");
//        } else if (result.getStatus() == ITestResult.SKIP) {
//            extentTest.skip("Test skipped");
//        }
//    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
