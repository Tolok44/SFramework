package report;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

//Esta clase es la que se va a modificar

public class BaseTestNG {
	
		public String rptFilename = System.getProperty("user.dir") + "\\target\\ExtentReportResultsSelenium.html";
		public WebDriver driver = null;
						
		private String getScreenShotsDir( ) {
			File file = new File(rptFilename);
			return file.getParent();
		}
		
		@AfterMethod
		public void afterMethod(ITestResult result) {
			ExtentReports extent = createReport();
			String testName = result.getName();
			String headerMessage = "-";
			String logMessage = "-";
			LogStatus logStatus = LogStatus.PASS;
			Throwable e = null;
		   if (result.getStatus() == ITestResult.FAILURE) {
			   saveScreenShot(testName);
			   e = result.getThrowable();
			   headerMessage =  "Test failed, click here for further details";
			   logMessage = "Failure: " + e.toString();
			   logStatus = LogStatus.FAIL;   
		   }   
		   
		   ExtentTest test = extent.startTest(testName, headerMessage);
		   test.log(logStatus, logMessage);
		   flushReports(extent, test);
		}
	
		private void saveScreenShot(String testName) {
			if (driver != null) {
				// take a screenshot
				TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		        File destFile = getDestinationFile(testName);
		        try {
		            FileUtils.copyFile(scrFile, destFile);
		        } catch (IOException ioe) {
		            throw new RuntimeException(ioe);
		        }
			}
		}
		
		private ExtentReports createReport() {
			ExtentReports extent = new ExtentReports(this.rptFilename, false);
			extent.addSystemInfo("Host Name", "SoftwareTestingMaterial").addSystemInfo("Environment", "Automation Testing")
					.addSystemInfo("User Name", "SDET Batch 1 - Reporting Team");
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
			return extent;
		}
		
		private File getDestinationFile(String testName) {
	        String userDirectory = 	getScreenShotsDir();
	        String date = getDateTime();
	        String fileName = testName + "_" + date + ".png";
	        //add date of today
	        String dateForDir = date.substring(0, 10);
	        String absoluteFileName = userDirectory + "/" + dateForDir + "/" + fileName;
	 
	        return new File(absoluteFileName);
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