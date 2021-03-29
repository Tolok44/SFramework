package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import excelManager.ReadExcelFile;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import excelManager.GetTCData;
import readObject.ReadObject;
import runner.GUI;
import runner.SMethods;
import testCase.StepSelenium;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class RunnerSelenium {

	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	String path;
	String fileName;
	String tcSelected;
	String reportFilename;
	String choosedDriver = "chrome";

	@BeforeClass
	public void setClass() throws IOException {
		reportFilename = String.format("target/report_%s.html", getDateTime());
		htmlReporter = new ExtentHtmlReporter(reportFilename);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		System.setProperty("webdriver.firefox.driver", ".\\Driver\\geckodriver.exe");
	}

	@DataProvider(name = "pasos")
	Object[][] getData() throws IOException {
		GUI gui = new GUI();
		List<List<StepSelenium>> testCases = new ArrayList<>();
		String[] info = gui.showGui();
		path = info[0];
		fileName = info[1];
		tcSelected = info[2];
//		choosedDriver = info[3]; // chrome or firefox
		String[] cases = tcSelected.split("-");
		for (int i = 0; i < cases.length; i++) {
			List<StepSelenium> steps = GetTCData.getStepSelenium(path, fileName, cases[i]);
			testCases.add(steps);
		}

		Object[][] datos = new Object[testCases.size()][1];
		for (int row = 0; row < testCases.size(); row++) {
			datos[row][0] = testCases.get(row);
		}
		return datos;
	}
	
	private void initWebDriver() {
		if (choosedDriver.equals("chrome")) {
			driver = new ChromeDriver();
		} else {
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	// test de data provider
	@Test(dataProvider = "pasos")
	public void testCase(List<StepSelenium> pasos) throws IOException {
		ReadObject object = new ReadObject();
		Properties allObjects = object.getProperties();
		initWebDriver();
		SMethods fw = new SMethods(driver);
		StepSelenium paso1 = pasos.get(0);
		test = extent.createTest(paso1.getName(), paso1.getDescription());
		Integer stepNo = 1;
		Boolean TCFailed = false;
		for (StepSelenium step : pasos) {
			try {
				if (!TCFailed) {
					fw.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(),
							step.getValue(), step.getStepDescription());
					test.pass(getReportStepDescription(stepNo, step));
					if (step.getTakeScreenShot().equals("y")) {
						addDetails(test, step);
					}
				} else {
					test.skip(getReportStepDescription(stepNo, step));
				}
			} catch (Exception | AssertionError e) {
				test.fail(getReportStepDescription(stepNo, step));
				addDetails(test, step);
				test.error(escapeHtml4(e.getMessage()));
				TCFailed = true;
			}
			stepNo++;
		}
		driver.quit();
	}
	
	@AfterSuite
	public void tearDown() {
		extent.flush();
		driver.quit();
		try {
			Desktop.getDesktop().open(new File(reportFilename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getReportStepDescription(Integer stepNo, StepSelenium step) {
		return escapeHtml4(String.format("step #%s: %s", stepNo.toString(), step.getStepDescription()));
	}

	public String captureScreen() {
		TakesScreenshot newScreen = (TakesScreenshot) driver;
		return newScreen.getScreenshotAs(OutputType.BASE64);
	}

	private String getDateTime() {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
		String today = formatter.format(date);
		return today;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	private void addDetails(ExtentTest test, StepSelenium step) throws IOException {
		String[][] tableData = { { "keyword", escapeHtml4(step.getKeyword()) }, { "locator type", escapeHtml4(step.getLocatorType()) },
				{ "locator value", escapeHtml4(step.getLocatorValue()) }
				// ,{ "value", escapeHtml4(step.getValue()) }
		};
		Markup table = MarkupHelper.createTable(tableData);
		test.log(Status.INFO, table);

		if (!isAlertPresent()) {
			test.log(Status.INFO, "after step screenshot",
					MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreen()).build());
		} else {
			try {
				String b64ss = getRobotScreenCapture();
				test.log(Status.INFO, "after step ROBOT screenshot",
						MediaEntityBuilder.createScreenCaptureFromBase64String(b64ss).build());
			} catch (HeadlessException | AWTException | IOException ex) {
				System.out.println(ex.toString());
			}
		}
	}

	private String getRobotScreenCapture() throws HeadlessException, AWTException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", out);
		byte[] bytes = out.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);

	}
}