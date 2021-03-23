package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import excelManager.ReadExcelFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import excelManager.GetTCData;
import readObject.ReadObject;
import runner.GUI;
import runner.SMethods;
import testCase.StepSelenium;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class RunnerSelenium {


    WebDriver driver;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest test;
    String path;
    String fileName;
    String tcSelected;

    @BeforeClass
    public void setClass() throws IOException {
        htmlReporter = new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        // Set the driver if this is a selenium test
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();


        // you can customize the report name if omitted default will be used
        test = extent.createTest("MyFirstTest", "Sample description");


        GUI gui = new GUI();

        String[] info = gui.showGui();
        path = info[0];
        fileName = info[1];
        tcSelected = info[2];



    }

    @DataProvider(name="pasos")
    Object[][] getData() throws IOException{
        List<StepSelenium> steps = GetTCData.getStepSelenium(path, fileName, tcSelected);
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
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}