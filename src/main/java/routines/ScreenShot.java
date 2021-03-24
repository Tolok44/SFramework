package routines;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;

public class ScreenShot {

    public void captureScreenShot(WebDriver driver, int StepNumber, String Description) {

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./ScreenShot/ScreenShot/" + "Paso" + StepNumber +
                    "." + Description + ".jpg"));
            System.out.println("**********************************+***+");
            System.out.println("ScreenShot taken");
            System.out.println("**********************************+***+");

        } catch (Exception e) {
            System.out.println("**********************************+***+");
            System.out.println("ScreenShot failed");
            System.out.println("**********************************+***+");

        }
    }

    public static void errorScreenShot(WebDriver driver, int StepNumber, String Description, int ErrorNumber) {

        try {
            TakesScreenshot ts3 = (TakesScreenshot) driver;
            File source = ts3.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./ScreenShot/ErrorScreenshot/" + "Paso" + StepNumber +
                    "." + Description + ".Error" + ErrorNumber + ".jpg"));
            System.out.println("**********************************+***+");
            System.out.println("ScreenShot taken");
            System.out.println("**********************************+***+");
        } catch (Exception e) {
            System.out.println("**********************************+***+");
            System.out.println("ScreenShot failed");
            System.out.println("**********************************+***+");
        }
    }

    public void borderLine(WebDriver driver,String locatorValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", locatorValue, "color yellow; border: 2px solid yellow;");

        System.out.println("**********************************+***+");
        System.out.println("Object selected");
        System.out.println("**********************************+***+");
    }
}
