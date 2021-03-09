package runner;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

import excelManager.GetTCData;
import testCase.Step;

import java.util.ArrayList;
import java.util.List;


public class SMethods {

    WebDriver driver;

    public SMethods(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate(String url){
        driver.navigate().to(url);
    }

    public void click(String selector, String value){
        switch(selector){
            case "id":
                driver.findElement(By.id(value)).click();
                break;
            case "name":
                driver.findElement(By.name(value)).click();
                break;
            case "tagName":
                driver.findElement(By.tagName(value)).click();
                break;
            case "cssSelector":
                driver.findElement(By.cssSelector(value)).click();
                break;
            case "xpath":
                driver.findElement(By.xpath(value)).click();
                break;
        }
    }

    public void write(String selector, String LocatorValue, String text){
        switch(selector){
            case "id":
                driver.findElement(By.id(LocatorValue)).sendKeys(text);
                break;
            case "name":
                driver.findElement(By.name(LocatorValue)).sendKeys(text);
                break;
            case "tagName":
                driver.findElement(By.tagName(LocatorValue)).sendKeys(text);
                break;
            case "cssSelector":
                driver.findElement(By.cssSelector(LocatorValue)).sendKeys(text);
                break;
            case "xpath":
                driver.findElement(By.xpath(LocatorValue)).sendKeys(text);
                break;
        }
    }
}



/*
switch(selector){
            case "id":
                driver.findElement(By.id(value));
                break;
            case "name":
                driver.findElement(By.name(value));
                break;
            case "tagName":
                driver.findElement(By.tagName(value));
                break;
            case "cssSelector":
                driver.findElement(By.cssSelector(value));
                break;
            case "xpath":
                driver.findElement(By.xpath(value));
                break;
        }
 */