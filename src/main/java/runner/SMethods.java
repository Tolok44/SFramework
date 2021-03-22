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
            case "alertok":
                alertOk();
                return "";
            case "asserttextequals":
            	System.out.println(description);
            	String text = driver.findElement(getLocator(p, LocatorType, LocatorValue)).getText();
            	Assert.assertEquals(text, value);
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

        String monthLocator = "";
        String splitter[] = value.split("\\s+");
        String day = splitter[0];
        String month = splitter[1];
        String year = splitter[2];
        boolean yet = false;
        String subMonth = month.substring(0,3);
        String splitter2[] = LocatorValue.split("\\+");
        String dateLocation = splitter2[0] + value + splitter2[2];

        String pickYear = splitter2[0] + year + splitter2[2];
        String pickMonth = splitter2[0] + month + " " + year + splitter2[2];

        driver.findElement(getLocator(p, LocatorType, "Dic_btnChooseDateProject")).click();
        driver.findElement(By.xpath(pickYear)).click();
        driver.findElement(By.xpath(pickMonth)).click();
        driver.findElement(By.xpath(dateLocation)).click();



    }

    public void alertOk (){
        WebDriverWait wait30 = new WebDriverWait(driver, 30);
        wait30.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }



}


