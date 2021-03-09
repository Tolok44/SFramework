package runner;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

import excelManager.GetTCData;
import readObject.ReadObject;
import testCase.Step;

import java.util.ArrayList;
import java.util.List;



public class Runner {

    WebDriver driver;
    List<Step> steps;
    String url;
    Properties po;

    @BeforeClass
    public void testSetup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Training\\IdeaProjects\\selenium\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        GetTCData tcSteps = new GetTCData();
        ReadObject prop = new ReadObject();

        url = "http://amazon.com.mx";

        try {
            po = prop.getProperties();
            steps = tcSteps.getStepArray();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void hola(){
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

            }
        }
        driver.quit();
    }

}
