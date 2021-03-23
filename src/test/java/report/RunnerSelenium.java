package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import excelManager.GetTCData;
import readObject.ReadObject;
import runner.GUI;
import runner.SMethods;
import testCase.StepSelenium;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    String reportFilename;

    @BeforeClass
    public void setClass() throws IOException {
    	reportFilename = String.format("target/report_%s.html", getDateTime());
        htmlReporter = new ExtentHtmlReporter(reportFilename);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
        // Set the driver if this is a selenium test
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();


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
        
        Object[][] datos = new Object[testCases.size()][1];
        for(int row=0;row<testCases.size();row++) {
            datos[row][0]=testCases.get(row);
        }
        return datos;
    }


    //test data provider
    @Test(dataProvider="pasos")
    public void testCase(List<StepSelenium> pasos) throws IOException{
        ReadObject object = new ReadObject();
        Properties allObjects = object.getProperties();
        SMethods fw = new SMethods(driver);
        StepSelenium paso1 = pasos.get(0);
        test = extent.createTest(paso1.getName(), paso1.getDescription());
        Integer stepNo = 1;
        for(StepSelenium step : pasos) {
        	try {
        		// you can customize the report name if omitted default will be used
                fw.realizar(allObjects, step.getKeyword(), step.getLocatorType(), step.getLocatorValue(), step.getValue(), step.getStepDescription());
                test.pass(getReportStepDescription(stepNo, step));
                if (step.getTakeScreenShot().equals("y")) {  
                	addDetails(test, step);
                }
            }
            catch(Exception | AssertionError e) {
                test.fail(getReportStepDescription(stepNo, step));
                addDetails(test, step);
                test.error(e.getMessage());
            }
        	stepNo++;
        }
        
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
    	return String.format("step #%s: %s", stepNo.toString(), step.getStepDescription());
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
	
	private void addDetails(ExtentTest test, StepSelenium step) throws IOException {
		String [][] tableData = {
					{ "keyword", step.getKeyword()},
					{ "locator type", step.getLocatorType()},
					{ "locatr value", step.getLocatorValue()}	
			};
			Markup table = MarkupHelper.createTable(tableData);
			
			test.log(Status.INFO, table);
			test.log(Status.INFO, "screenshot",
					MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreen()).build());
	}

}