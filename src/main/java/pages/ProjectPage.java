package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectPage extends BasePageObjectModel {
	
	By dic_lnkNewProjectProject = By.xpath("//a[@mattooltip='Create a new project!']");
	By dic_btnStartDateProject=By.xpath("//form/div/div/div[2]/div/div/mat-form-field/div/div/div[2]/mat-datepicker-toggle/button");
	By dic_btnEndDateProject=By.xpath("//mat-datepicker-toggle[@ng-reflect-disabled='false']//span//*[local-name()='svg']");

	By dic_btnMenuProject = By.xpath("//mat-icon[text()='menu']");
	By dic_btnMyProfileProject = By.xpath("//span[text()='My Profile']");

	public ProjectPage(WebDriver driver) {
		super(driver);
		
	}

	public ProjectPage(WebDriver driver, int wait) {
		super(driver, wait);
		
	}
	
	public void newProject() {
		findAndClick(dic_lnkNewProjectProject);
	}
	
	public void openProjectMenu() {
		findAndClick(dic_btnMenuProject);
	}
	public void openMyProfile() {
		findAndClick(dic_btnMyProfileProject);
	}
	public void openStartDate() {
		findAndClick(dic_btnStartDateProject);
	}
}
