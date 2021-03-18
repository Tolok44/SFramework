package report;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelManager.GetTCData;
import readObject.ReadObject;
import runner.SMethods;
import testCase.StepSelenium;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

// create your test extending BaseTestNG to leverage reporting capabilities
public class RunnerSelenium extends BaseTestNG {


    WebDriver driver;
    List<StepSelenium> steps;
    String url;
    Properties po;

    @BeforeSuite
    public void beforeSuite() {
        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        // Set the driver if this is a selenium test
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //Obtain test case steps from excel file
        GetTCData tcSteps = new GetTCData();
        ReadObject prop = new ReadObject();
        // you can customize the report name if omitted default will be used
        rptFilename = System.getProperty("user.dir") + "\\target\\ExtentReportResultsSelenium.html";


        //Save test case steps on a list
        try {
            po = prop.getProperties();
            steps = tcSteps.getStepSelenium();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
            Assert.assertTrue(true);
        }
        catch(Exception e) {
            Assert.assertTrue(false);
        }

    }


    // tear down driver
    @AfterSuite
    public void afterSuite() {
        System.out.println("Tests finished");
        driver.quit();
    }
}