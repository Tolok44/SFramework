package report;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.Profile;
import pages.ProjectPage;

public class ScrumMetricsTest {

	@Test
	public void Test1() {
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized", "incognito");
		WebDriver driver = new ChromeDriver(options);
		LoginPage loginPage = new LoginPage(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		Profile profile = new Profile(driver);

		loginPage.navigate();
		loginPage.loginWithUsernameAndPassword("tstuser1", "Admin!100");
//		projectPage.newProject();
		projectPage.openProjectMenu();
		projectPage.openMyProfile();
		profile.clickUserPhotoUpload();

		// ----------option 1-------------
		// copy image path to the clipboard
		StringSelection ss = new StringSelection("C:\\Users\\hexaware\\Pictures\\Foto para ID avatar.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		// native key strokes for CTRL, V and ENTER keys
		Robot robot;
		try {
			Thread.sleep(1000); // is this necessary ?
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {

			e.printStackTrace();
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		// ----------option 2-------------
		// skip the file dialog type the file name directly to the file input
		// profile.setImageFile("C:\\Users\\hexaware\\Pictures\\Foto para ID
		// avatar.jpg");
		// profile.save();

		// ----------- option 3-------------
		// Upload a file using AutoIT ?

	}

}
