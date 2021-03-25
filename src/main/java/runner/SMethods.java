package runner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Properties;



public class SMethods {

    WebDriver driver;

    public SMethods(WebDriver driver) {
        this.driver = driver;

    }

    public void navigate(String url){
        driver.navigate().to(url);
    }



    public void write(String selector, String LocatorValue, String text){
        this.typeOfAction(selector, LocatorValue).sendKeys(text);
    }

    public void clear(String selector, String locatorValue){
        this.typeOfAction(selector, locatorValue).clear();
    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public void click(By by){
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public boolean elementExists(String type, String lcoatorValue){

        switch(type){
            case "id":
                if (driver.findElement(By.id(lcoatorValue))!=null){
                    return true;
                }
                break;
            case "name":
                if(driver.findElement(By.name(lcoatorValue))!=null){
                  return true;
                }
                break;
            case "tagName":
                if(driver.findElement(By.tagName(lcoatorValue))!=null){
                    return true;
                }
                break;
            case "css":
                if(driver.findElement(By.cssSelector(lcoatorValue))!=null){
                    return true;
                }
                break;
            case "xpath":
                if(driver.findElement(By.xpath(lcoatorValue))!=null){
                    return true;
                }
                break;
            default:
                return false;
        }
            return false;
    }



    public WebElement typeOfAction(String type, String value){
        WebElement a;

        switch(type){
            case "id":
                a = driver.findElement(By.id(value));
            break;
            case "name":
                a = driver.findElement(By.name(value));
                break;
            case "tagName":
                a = driver.findElement(By.tagName(value));
                break;
            case "cssSelector":
                a = driver.findElement(By.cssSelector(value));
                break;
            case "xpath":
                a = driver.findElement(By.xpath(value));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return a;
    }







    public String realizar(Properties p, String keyword, String LocatorType, String LocatorValue, String value, String description) throws Exception {
        System.out.println("realizando operacion....");
        switch(keyword.toLowerCase()){
            case "goto":
                System.out.println(description);
                driver.get(p.getProperty(value));
                return "";
            case "click":
                System.out.println(description);
                click(getLocator(p,LocatorType, LocatorValue));
                return "";
            case "type":
                System.out.println(description);
                driver.findElement(getLocator(p, LocatorType, LocatorValue)).sendKeys(value);
                return "";
            case "gettext":
                System.out.println(description);
                return driver.findElement(getLocator(p, LocatorType, LocatorValue)).getText();
            case "clear":
                System.out.println(description);
                driver.findElement(getLocator(p, LocatorType, LocatorValue)).clear();
                return "";
            case "refresh":
                System.out.println(description);
                driver.navigate().refresh();
                return "";
            case "select":
                System.out.println(description);
                selectAction(p, LocatorType, LocatorValue, value);
                return "";
            case "comparetext":
                System.out.println(description);
                if (compareText(driver.findElement(getLocator(p, LocatorType, LocatorValue)), value)){
                    return "";
                }else{
                    throw new Exception("El texto a comparar no es igual");
                }
            case "date":
                calendar(p, LocatorType, LocatorValue, value);
                return "";
            case "open_project":
                projList(p, LocatorType, LocatorValue, value);
                return "";
            case "alertok":
                alertOk();
                return "";
            case "verify_text":
            	System.out.println(description);
            	String text = driver.findElement(getLocator(p, LocatorType, LocatorValue)).getText();
            	Assert.assertEquals(text, value);
            	return "";
            case "verify_alert_text":
            	System.out.println(description);
               	Assert.assertEquals(getAlertText(), value);
            	return "";
            case "submit":
            	System.out.println(description);
                submit(getLocator(p,LocatorType, LocatorValue));
                return "";
            case "uplphoto":
            	System.out.println(description);
            	selectPhoto(value);
            	return "";
            case "wait":
                Thread.sleep(1000);
                return "";
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
                return By.cssSelector(p.getProperty(LocatorValue));
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


    public void selectAction(Properties p, String LocatorType, String LocatorValue, String value) throws Exception {
        Select item = new Select(driver.findElement(getLocator(p, LocatorType,LocatorValue)));
        item.selectByValue(value);
    }

    public boolean compareText(WebElement e, String value) {
        if (e.getText().equals(value)){
            return true;
        }else{
            return false;
        }
    }

    public void calendar(Properties p, String LocatorType, String LocatorValue, String value) throws Exception {

        String splitter[] = value.split("\\s+");
        String month = splitter[1];
        String year = splitter[2];
        String splitter2[] =p.getProperty(LocatorValue).split("\\+");
        String dateLocation = splitter2[0] + value + splitter2[2];

        String pickYear = splitter2[0] + year + splitter2[2];
        String pickMonth = splitter2[0] + month + " " + year + splitter2[2];
        
        driver.findElement(getLocator(p, LocatorType, "Dic_btnStartDateProject")).click();
        driver.findElement(getLocator(p, LocatorType, "Dic_btnChooseDateProject")).click();
        driver.findElement(By.xpath(pickYear)).click();
        driver.findElement(By.xpath(pickMonth)).click();
        driver.findElement(By.xpath(dateLocation)).click();
    }
    
    public void projList(Properties p, String LocatorType, String LocatorValue, String value) throws Exception {
    	Thread.sleep(2000);
    	String splitter[] = p.getProperty(LocatorValue).split("\\+");
    	String projLocator = splitter[0] + value + splitter[2];
    	String openButton = projLocator + "/parent::span/parent::mat-expansion-panel-header/following-sibling::div/div/div/button";
    	driver.findElement(By.xpath(projLocator)).click();
    	driver.findElement(By.xpath(openButton)).click();
    	
    }
    
    public Alert getAlert() {
    	WebDriverWait wait30 = new WebDriverWait(driver, 30);
        wait30.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert;
    }

    public void alertOk (){
        getAlert().accept();
    }

    public String getAlertText (){
        return getAlert().getText();
    }

    public void submit(By by){
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(by)).submit();
    }
    
    public void selectPhoto(String path) {
		// Copia imagen al Clipboard
		StringSelection ss = new StringSelection(path);
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
	}

}


