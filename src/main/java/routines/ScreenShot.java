package routines;

import io.qameta.allure.Allure;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
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

    public void addingScreenShotAllure(WebDriver driver) {
        try {
            Allure.addAttachment("Screenshot has been added", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
            System.out.println("**********************************+***+");
            System.out.println("Screenshot has been added successfully on Allure");
            System.out.println("**********************************+***+");

        } catch (Exception e) {
            System.out.println("**********************************+***+");
            System.out.println("The screenshot has not been added");
            System.out.println("**********************************+***+");
        }
    }
}
