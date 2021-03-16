package test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import excelManager.GetTCData;
import front_end.FrontOperations;
import readObject.ReadObject;
import testCase.StepSelenium;

public class Extent {
	WebDriver webdriver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeClass
	public void setClass() {
		htmlReporter = new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		webdriver = new ChromeDriver();
		test = extent.createTest("MyFirstTest", "Sample description");
	}
	
	@DataProvider(name="pasos")
	Object[][] getData() throws IOException{
		List<StepSelenium> steps = GetTCData.getStepSelenium();
		Object[][] datos = new Object[steps.size()][1];
		for(int row=0;row<steps.size();row++) {
			datos[row][0]=steps.get(row);
			}
		return datos;
	}
	
	@Test(dataProvider="pasos")
	public void testCase(StepSelenium step) throws Exception{
		ReadObject object = new ReadObject();
		Properties allObjects = object.getProperties();
		FrontOperations operation = new FrontOperations(webdriver);
			try {
				operation.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(), step.getValue());
				test.pass(step.getStepDescription());
			}
			catch(Exception e) {
				test.fail(step.getStepDescription());
				test.error(e);
			}
			
	}
	
	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
}
