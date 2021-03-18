package runner;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

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

    public boolean elementExists(String type, String lcoatorValue){
        WebElement a;

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
            case "cssSelector":
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
                driver.findElement(getLocator(p, LocatorType, LocatorValue)).click();
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
        item.selectByVisibleText(value);
    }

    public boolean compareText(WebElement e, String value) {
        if (e.getText().equals(value)){
            return true;
        }else{
            return false;
        }

    }
}



