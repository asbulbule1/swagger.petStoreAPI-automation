package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter implements ITestListener {

    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
    private static String reportName;

    public synchronized static ExtentReports getExtentInstance() {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            reportName = "Extent-Report-" + timeStamp + ".html";
            String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportName;

            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("API Automation Test Report");
            sparkReporter.config().setReportName("API Test Execution Summary");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Host Name", "PetStore");
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }

    @Override
    public void onStart(ITestContext context) {
        getExtentInstance(); // initialize once
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest methodNode = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestContext().getName())
                .assignAuthor(System.getProperty("user.name"));

        testNode.set(methodNode);
        methodNode.info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = testNode.get();
        if (test != null) {
            test.log(Status.PASS, "Test Passed");
            test.info("Execution Time: " + getExecutionTime(result) + " ms");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testNode.get();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            test.info("Execution Time: " + getExecutionTime(result) + " ms");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = testNode.get();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
            test.info("Execution Time: " + getExecutionTime(result) + " ms");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
        //openReport();
    }

    private void openReport() {
        try {
            File reportFile = new File(System.getProperty("user.dir") + "\\reports\\" + reportName);
            Desktop.getDesktop().browse(reportFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getExecutionTime(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}
