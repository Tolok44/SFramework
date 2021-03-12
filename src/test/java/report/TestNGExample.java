package report;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

// create your test extending BaseTestNG to leverage reporting capabilities
public class TestNGExample extends BaseTestNG {

	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		// Set the driver if this is a selenium test
		driver = new ChromeDriver();
		// you can customize the report name if omitted default will be used
		rptFilename = System.getProperty("user.dir") + "\\target\\ExtentReportResultsSelenium.html";
	}

	// 2 example Tests
	
	@Test
	public void myPassingExampleTest() {
		driver.navigate().to("https://www.google.com");
		int result = 1 + 1;
		Assert.assertEquals(result, 2);
	}

	@Test
	public void myFailExampleTest() {
		// screenshot of failing tests will be saved by the base class
		driver.navigate().to("https://www.google.com");
		int result = 1 + 1;
		Assert.assertEquals(result, 3);
	}

	// tear down driver
	@AfterSuite
	public void afterSuite() {
		System.out.println("Tests finished");
		driver.quit();
	}
}
