package front_end;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrontOperations {
	WebDriver driver;
	
	public FrontOperations(WebDriver driver) {
		this.driver = driver;
	}
	public String realizar(Properties p, String keyword, String LocatorType, String LocatorValue, String value) throws Exception {
		System.out.println("realizando operacion....");
		switch(keyword.toLowerCase()){
		case "goto":
			driver.get(p.getProperty(value));
			return "";
		case "click":
			driver.findElement(getLocator(p, LocatorType, LocatorValue)).click();
			return "";
		case "typetext":
			driver.findElement(getLocator(p, LocatorType, LocatorValue)).sendKeys(value);
			return "";
		case "gettext":
			return driver.findElement(getLocator(p, LocatorType, LocatorValue)).getText();
		default:
			throw new Exception("Keyword erronea");
		}
	}
	
	private By getLocator(Properties p, String LocatorType, String LocatorValue) throws Exception {
		switch(LocatorType.toLowerCase()) {
		case "id":
			return By.id(p.getProperty(LocatorValue));
		case "xpath":
			return By.xpath(p.getProperty(LocatorValue));
		case "css":
			return By.xpath(p.getProperty(LocatorValue));
		case "name":
			return By.name(p.getProperty(LocatorValue));
		case "class":
			return By.className(p.getProperty(LocatorValue));
		case "text":
			return By.linkText(p.getProperty(LocatorValue));
		case "partial text":
			return By.linkText(p.getProperty(LocatorValue));
		default:
			throw new Exception("Wrong object type");
		}
	}
}
