package report;
// package ...

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EReport extends TestWatcher {
	private String rptFilename;
	private String screenShotsDir;
	private WebDriver browser = null;

	private void setScreenShotsDir( ) {
		File file = new File(rptFilename);
		screenShotsDir = file.getParent();
	}
	
	public EReport(String rptFilename) {
		this.rptFilename = rptFilename;
		setScreenShotsDir();
	}

	public EReport(String rptFilename, WebDriver browser) {
		this.rptFilename = rptFilename;
		this.browser = browser;
		setScreenShotsDir();
	}

	@Override
	protected void failed(Throwable e, Description description) {
		if (browser != null) {
			// take a screenshot
			System.out.println("saving screenshot...");
			TakesScreenshot takesScreenshot = (TakesScreenshot) browser;
			 
	        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	        File destFile = getDestinationFile(description);
	        System.out.println("saving:" + destFile);
	        try {
	            FileUtils.copyFile(scrFile, destFile);
	        } catch (IOException ioe) {
	            throw new RuntimeException(ioe);
	        }
		}
		ExtentReports extent = createReport();
		ExtentTest test = extent.startTest(description.getDisplayName(), "Test failed, click here for further details");
		
		// step log
		test.log(LogStatus.FAIL, "Failure: " + e.toString());

		flushReports(extent, test);
	}

	@Override
	protected void succeeded(Description description) {
		ExtentReports extent = createReport();
		ExtentTest test = extent.startTest(description.getDisplayName(), "-");

		// step log
		test.log(LogStatus.PASS, "-");
		flushReports(extent, test);
	}

	private ExtentReports createReport() {

		ExtentReports extent = new ExtentReports(this.rptFilename, false);
		extent.addSystemInfo("Host Name", "SoftwareTestingMaterial").addSystemInfo("Environment", "Automation Testing")
				.addSystemInfo("User Name", "SDET Batch 1 - Reporting Team");

		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

		return extent;
	}
	
	private File getDestinationFile(Description description) {
        String userDirectory = screenShotsDir;
        String date = getDateTime();
        String fileName = description.getDisplayName() + "_" + date + ".png";
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
