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
import java.util.ArrayList;
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


    }

    @DataProvider(name="pasos")
    Object[][] getData() throws IOException{
        GUI gui = new GUI();
        List<List<StepSelenium>> testCases = new ArrayList<>();
        String[] info = gui.showGui();
        path = info[0];
        fileName = info[1];
        tcSelected = info[2];
        String[] cases = tcSelected.split("-");
        for(int i=0; i<cases.length;i++) {
            List<StepSelenium> steps = GetTCData.getStepSelenium(path, fileName, cases[i]);
            testCases.add(steps);
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Object[][] datos = new Object[testCases.size()][1];
        for(int row=0;row<testCases.size();row++) {
            datos[row][0]=testCases.get(row);
        }
        return datos;
    }


    //test de data provider
    @Test(dataProvider="pasos")
    public void testCase(List<StepSelenium> pasos) throws IOException{
        ReadObject object = new ReadObject();
        Properties allObjects = object.getProperties();
        SMethods fw = new SMethods(driver);
        StepSelenium paso1 = pasos.get(0);
        test = extent.createTest(paso1.getName(), paso1.getDescription());
        for(StepSelenium step : pasos) {
            try {
                // you can customize the report name if omitted default will be used
                fw.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(), step.getValue(), step.getStepDescription());
                test.pass(step.getStepDescription());
            }
            catch(Exception | AssertionError e) {
                test.fail(step.getStepDescription());
                test.error(e.getMessage());
            }
        }

    }


    @AfterSuite
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}