package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Profile extends BasePageObjectModel {
	By dic_btnMyProfileProject = By.xpath("//span[text()='My Profile']");
	By dic_lnkChangePhotoProfile = By.xpath("//*[@id='changephoto']");
	By dic_imgUserPhotoProject = By.xpath("//img[@class='user-photo']");
	By profileImageFileInput = By.xpath("//*[@id=\"fulldialog\"]/form/div[3]/div/div[1]/div/input");
	By dic_btnSaveProfile = By.xpath("//span[text()='Save']");

	public Profile(WebDriver driver) {
		super(driver);
		
	}

	public Profile(WebDriver driver, int wait) {
		super(driver, wait);
	}
	
	public void openProfile() {
		findAndClick(dic_btnMyProfileProject);
	}
	
	public void clickUserPhotoUpload() {
		findAndClick(dic_lnkChangePhotoProfile);
	}
	
	public void setImageFile(String path) {
		this.type(path, profileImageFileInput);
	}

	public void save() {
		findAndClick(dic_btnSaveProfile);
	}
}
