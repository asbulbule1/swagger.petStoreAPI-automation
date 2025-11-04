package utils;

import io.qameta.allure.Allure;
import org.testng.Reporter;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class BaseTest {

	private static final String LOG_FOLDER_NAME = "logs";
	private File logFile;
	private PrintStream logStream;

	protected static ConfigReader configReader; // ✅ static - initialize once
	protected RequestLoggingFilter requestLoggingFilter;
	protected ResponseLoggingFilter responseLoggingFilter;

	// global setup
	@BeforeSuite
	public void globalSetup() {
		configReader = new ConfigReader();
		RestAssured.baseURI = configReader.getProperty("BASE_URL");

		// Clean Allure results folder before each suite run
		File allureResultsDir = new File(System.getProperty("user.dir") + "/allure-results");
		if (allureResultsDir.exists()) {
			for (File file : allureResultsDir.listFiles()) {
				file.delete();
			}
		}
	}

	// logging starts here
	@BeforeMethod
	public void setupPerTest(Method method) {
		try {
			// checking whether log folder exists
			String projectDir = System.getProperty("user.dir");
			String logsFolderPath = projectDir + File.separator + LOG_FOLDER_NAME;
			File logDir = new File(logsFolderPath);
			if (!logDir.exists())
				logDir.mkdirs();

			String logFilePath = logsFolderPath + File.separator + method.getName() + "_" + System.currentTimeMillis()
					+ ".log";
			logFile = new File(logFilePath);

			FileOutputStream fos = new FileOutputStream(logFile, false);
			logStream = new PrintStream(fos, true);

			requestLoggingFilter = new RequestLoggingFilter(logStream);
			responseLoggingFilter = new ResponseLoggingFilter(logStream);
			RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

			// for console purpose only
			Reporter.log("--------------------------------------------------", true);
			Reporter.log("START Running Test: " + method.getName(), true);
			Reporter.log("--------------------------------------------------", true);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize logging: " + e.getMessage());
		}
	}

	// attach log to report and cleanup
	@AfterMethod
	public void attachLogsToAllure(ITestResult result) {
		try {
			// Close stream before reading/deleting
			if (logStream != null) {
				logStream.flush();
				logStream.close();
			}

			// for console only -
			String status = "unknow";
			String testName = result.getMethod().getMethodName();
			if (result.getStatus() == ITestResult.SUCCESS) {
				status = "PASSED";
				Reporter.log("PASSED " + testName, true);
			} else if (result.getStatus() == ITestResult.FAILURE) {
				status = "FAILED";
				Reporter.log("FAILED " + testName, true);
			}
			
			
			

			// attaching log file to allure
			if (logFile != null && logFile.exists()) {
				byte[] fileBytes = Files.readAllBytes(logFile.toPath());

				Allure.addAttachment(testName + " - " + status + " - API Execution Log", "text/plain",
						new ByteArrayInputStream(fileBytes), ".log");

				Files.deleteIfExists(logFile.toPath());
			} else {
				System.out.println("No log file found for " + result.getMethod().getMethodName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to attach/delete log for " + result.getMethod().getMethodName());
		}
	}
}
