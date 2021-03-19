package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObjectModel {

	protected WebDriver driver;
	public int wait = 10;

	public BasePageObjectModel() {
		
	}

	public BasePageObjectModel(WebDriver driver) {
		this.driver = driver;
	}

	public BasePageObjectModel(WebDriver driver, int wait) {
	
		this.driver = driver;
		this.wait = wait;
	}

	public void findAndClick(By by) {
		WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		String elementTxt = element.getText();
		if (elementTxt.isEmpty()) {
			elementTxt = by.toString();
		}
		System.out.println("<<" + elementTxt + ">> CLICKED!!");
		element.click();
	}

	public WebElement findElement(By by) {
		return new WebDriverWait(driver, wait).until(ExpectedConditions.presenceOfElementLocated(by));

	}

	public void type(String text, By by) {
		WebElement element = findElement(by);
		element.sendKeys(text);
	}
	
	public Boolean isPresent(By by) {
		List<WebElement> elements = driver.findElements(by);
		if (elements.isEmpty()) {
			return false;
		}
		return true;
	}
}
