package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObjectModel {
	String dic_btnLoginStartHome = "//button[contains(text(),\"Login\")]";
	By dic_txtUserNameLogin = new By.ByXPath("//input[@placeholder=\"Username\"]");
	By dic_txtUserPasswordLogin = new By.ByXPath("//input[@placeholder=\"Password\"]");
	By dic_btnLoginLogin = new By.ByXPath("//button[@type=\"submit\"]");
	String url = "https://scrum-metrics.herokuapp.com/start/login";

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void typeUsername(String username) {
		this.type(username, dic_txtUserNameLogin);
	}

	public void typePassword(String password) {
		this.type(password, dic_txtUserPasswordLogin);
	}

	public void loginWithUsernameAndPassword(String username, String password) {
		typeUsername(username);
		typePassword(password);
		findAndClick(dic_btnLoginLogin);
	}

	public void navigate() {
		driver.get(url);
	}

}
