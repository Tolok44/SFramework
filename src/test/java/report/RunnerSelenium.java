package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import excelManager.GetTCData;
import readObject.ReadObject;
import routines.ScreenRecorder;
import runner.SMethods;
import testCase.StepSelenium;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class RunnerSelenium {


    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setClass() throws IOException, AWTException {
        htmlReporter = new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        /*
        * Here we change the path property to driverManager because "It works on different OS and Browsers"
        */
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        /*
         * Here we Start Screen Recording
         */
        ScreenRecorder.startScreenRecording();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();


        // you can customize the report name if omitted default will be used
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


    //test de data provider
    @Test(dataProvider="pasos")
    public void testCase(StepSelenium step) throws IOException{
        ReadObject object = new ReadObject();
        Properties allObjects = object.getProperties();
        SMethods fw = new SMethods(driver);
        try {
            fw.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(), step.getValue(), step.getStepDescription());
            test.pass(step.getStepDescription());
        }
        catch(Exception | AssertionError e) {
            test.fail(step.getStepDescription());
            test.error(e.getMessage());
        }
     }


    @AfterSuite
    public void tearDown() throws IOException {
        extent.flush();
        driver.quit();
        /*
        * Here we Stop Screen Recording
         */
        ScreenRecorder.stopScreenRecording();
    }
}