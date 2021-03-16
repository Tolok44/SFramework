package report;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import excelManager.GetTCData;
import readObject.ReadObject;
import runner.SMethods;
import testCase.Step;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

// create your test extending BaseTestNG to leverage reporting capabilities
public class RunnerSelenium extends BaseTestNG {


    WebDriver driver;
    List<Step> steps;
    String url;
    Properties po;

    @BeforeSuite
    public void beforeSuite() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Training\\IdeaProjects\\selenium\\drivers\\chromedriver.exe");
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
            steps = tcSteps.getStepArray();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    // Test case step execution loop

    @Test
    public void runner(){
        SMethods fw = new SMethods(driver);

        for(int i=0; i<steps.size(); i++){
            steps.get(i);
            switch(steps.get(i).getKeyword()){
                case "GOTO":
                    fw.navigate(po.getProperty("url"));
                    break;
                case "TYPETEXT":
                    fw.write(steps.get(i).getLocatorType(), po.getProperty(steps.get(i).getLocatorValue()), steps.get(i).getValue());
                    break;
                case "CLICK":
                    fw.click(steps.get(i).getLocatorType(), po.getProperty(steps.get(i).getLocatorValue()));
                    break;
            }
        }
    }



    // tear down driver
    @AfterSuite
    public void afterSuite() {
        System.out.println("Tests finished");
        driver.quit();
    }
}