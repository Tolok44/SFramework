package runner;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class SMethods {

    WebDriver driver;

    public SMethods(WebDriver driver) {
        this.driver = driver;

    }

    public void navigate(String url){
        driver.navigate().to(url);
    }

    public void click(String selector, String value){
        this.typeOfAction(selector, value).click();
    }

    public void write(String selector, String LocatorValue, String text){
        this.typeOfAction(selector, LocatorValue).sendKeys(text);
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
}



