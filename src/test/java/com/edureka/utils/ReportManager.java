package com.edureka.utils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.edureka.setup.BaseSteps;

public class ReportManager extends BaseSteps {
	

	public static ExtentReports extent;
	public ExtentTest test;
	public ExtentSparkReporter sparkReport;

	@BeforeSuite
	public void reportGeneration() {
		
		String timStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ss HH-mm-ss"));
		String reportPath = System.getProperty("user.dir") + "/target/Reports/EdurekaReport" + timStamp + ".html";
		sparkReport = new ExtentSparkReporter(reportPath);
	
		//configure extent reports
		sparkReport.config().setTheme(Theme.DARK);
		sparkReport.config().setDocumentTitle("Edureka Automation Report");
		sparkReport.config().setReportName("Functional Test Report");
		sparkReport.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
		extent = new ExtentReports();
		extent.attachReporter(sparkReport);
		
		//System info
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester Name", "Zane Falcao");
	}

	@BeforeMethod
	public void reportTestCreation(Method method) {
		test = extent.createTest("Test Case: " + method.getName());
	}
	
	@AfterMethod
	public void reportTestCompletion(ITestResult result) {
		
		String screenShotPath = ScreenShotTest.capture(driver, result.getMethod().getMethodName());
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.addScreenCaptureFromPath(screenShotPath);
			test.pass("Test Passed");
		}else if (result.getStatus() == ITestResult.FAILURE) {
			test.addScreenCaptureFromPath(screenShotPath);
//			test.fail(result.getThrowable());
			test.fail("Test Failed");
		}else if (result.getStatus() == ITestResult.SKIP) {
			test.addScreenCaptureFromPath(screenShotPath);
			test.skip("Test Skipped");
		}
	}
	
	@AfterSuite
	public void reportCompletion() {
		extent.flush();
	}

}
