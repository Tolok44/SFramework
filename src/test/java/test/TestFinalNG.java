package test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelManager.GetTCData;
import front_end.FrontOperations;
import readObject.ReadObject;
import testCase.StepSelenium;

public class TestFinalNG {
	WebDriver webdriver;
	@BeforeClass
	public void setClass() {
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		webdriver = new ChromeDriver();
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
	public void testCase(StepSelenium step) throws IOException{
		ReadObject object = new ReadObject();
		Properties allObjects = object.getProperties();
		FrontOperations operation = new FrontOperations(webdriver);
			try {
				operation.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(), step.getValue());
				Assert.assertTrue(true);
			}
			catch(Exception e) {
				Assert.assertTrue(false);
			}
			
	}
	
}
