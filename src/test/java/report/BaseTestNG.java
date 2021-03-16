package report;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTestNG {

	public String rptFilename = System.getProperty("user.dir") + "\\Reports\\Extent Report Results_"+ getCurrentDateTime() + ".html";
	public WebDriver driver = null;

	public static String getCurrentDateTime() {
		String formato = "yyyyMMdd_HHmmss";
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
		LocalDateTime ahora = LocalDateTime.now();
		return formateador.format(ahora);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		ExtentReports extent = createReport();
		String testName = result.getName();
		String headerMessage = "-";
		String logMessage = "-";
		ExtentTest test = extent.startTest(testName, headerMessage);

		LogStatus logStatus = LogStatus.PASS;
		Throwable e = null;
		if (result.getStatus() == ITestResult.FAILURE) {
			e = result.getThrowable();
			headerMessage = "Test failed, click here for further details";
			logMessage = "Failure: " + e.toString();
			logStatus = LogStatus.FAIL;

			// Here take a screenshot when test fail
			try {
				// test.log(LogStatus.FAIL, test.addScreenCapture(getScreenshot(driver,getDateTime())) + "Test Failed");
				test.log(LogStatus.FAIL, test.addScreenCapture(captureScreen(driver, getDateTime())) + "Test Failed");
			} catch (Exception ioe) {
				throw new RuntimeException(ioe);
			}
		}
		test.log(logStatus, logMessage);
		flushReports(extent, test);
	}

	// This method don't save the Screenshot in a local folder, but use BASE64. It shown image OK
	public static String captureScreen(WebDriver driver, String screenshotName) {
		TakesScreenshot newScreen = (TakesScreenshot) driver;
		String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
		return "data:image/jpg;base64, " + scnShot;
	}

	// Method to take the Screenshot and save it in a local folder
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "\\Reports\\FailedTestsScreenshots\\" + screenshotName
				+ dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	private ExtentReports createReport() {
		ExtentReports extent = new ExtentReports(this.rptFilename, false);
		extent.addSystemInfo("Host Name", "SoftwareTestingMaterial").addSystemInfo("Environment", "Automation Testing")
				.addSystemInfo("User Name", "SDET Batch 1 - Reporting Team");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		return extent;
	}

	private String getDateTime() {
		Date date = Calendar.getInstance().getTime();
		// Display a date in day, month, year format
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
		String today = formatter.format(date);
		return today;
	}

	private void flushReports(ExtentReports extent, ExtentTest test) {
		// ending test
		extent.endTest(test);
		// writing everything to document
		extent.flush();
	}
}