package PageObjects;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;




/*
 * Author
 * Afsal Backer
 *
 *  This class contains all helper and wrapper methods that can be used in test classes.
 */



public class Page
{
	public static final Logger LOGGER = LogManager.getLogger(Page.class);

	WebDriver driver;

	public Page(WebDriver driver) 
	{
		this.driver = driver;
	}

	public void pause(long time) 
	{
		try 
		{
			Thread.sleep(time);
		} 
		catch (Exception e) 
		{
		}
	}   

	
	//Method to select a value from a dropdown
	public void selectDropdown(WebElement element, String text)
	{
		Select dropDownBox = new Select(element);
		dropDownBox.selectByVisibleText(text);
	}
	

	//Method to get current date in MM/dd/yyyy
	public String currentDate()
	{
		Calendar currentDate = Calendar.getInstance(); //Get the current date
		SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy"); //format it as per your requirement
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	

	//Method to generate a random name from current system datetime
	public String randomNameGenerator()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		
		String name = dtf.format(now);
		
		return(name.replaceAll("[^a-zA-Z0-9]", ""));
		
	}
	

																			//PAGE ELEMENT LOCATORS AND OPERATIONS METHODS START


	
	
	//Wait for element to be visible. Parameter - Pagefactory element
	public WebDriver waitForElementVisbility(WebElement element) throws InterruptedException
	{
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.visibilityOf(element));
			LOGGER.info("Element visible.");
		return (driver);
	}
	
	//Wait for element to be visible. Parameter -  element locator
	public WebDriver waitForDynamicElementVisbility(By element) throws InterruptedException
	{	
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			LOGGER.info("Element found.");
		
		return (driver);
	}
	
	//Wait for element to be present. Parameter - element locator
	public WebDriver waitForElementToBePresent(By element) throws InterruptedException
	{
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			LOGGER.info("Element visible.");
		return (driver);
	}
	
	//Wait for element to be clickable. Parameter - Pagefactory element
	public WebDriver waitForClickableElement(WebElement element) throws InterruptedException
	{
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LOGGER.info("Element clickable.");
		
		return (driver);
	}
	
	//Wait for element to be clickable. Parameter - element locator
	public WebDriver waitForClickableDynamicElement(By element) throws InterruptedException
	{
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LOGGER.info("Element found.");
		
		return (driver);
	}
	
	//Wait for element to be invisible. Parameter - element locator
	public WebDriver waitForElementToBeInvisble(By element) throws InterruptedException
	{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			LOGGER.info("Element invisible.");
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);  
			
		return (driver);
	}

	//Verify element is visible on the page.
	public boolean isElementVisible(By locator) 
	{
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
	    boolean result = ExpectedConditions.invisibilityOfElementLocated(locator).apply(driver);
	    driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS); 
	    return !result;
	}

	
	
	//Verify element is present on the DOM. Parameters - driver, locator path. Waits for long before failing
	public boolean isElementPresent(WebDriver driver, By by) 
	{  
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
	    try 
	    {  
	        driver.findElement(by);  
	        LOGGER.info("Element present.");
	        return true;  
	    } 
	    catch (NoSuchElementException e) 
	    {  
	        LOGGER.info("Element not present.");
	    	return false;  
	    } 
	    finally 
	    {  
	        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);  
	    }  
	}
	
	
																		//PAGE ELEMENT WAIT METHODS END

	

	
																	//Element Interaction Helper Methods Start
	

	//Method to perform a click after element is loaded - parameter is page factory element
		public void click(WebElement element) throws InterruptedException
		{
			waitForClickableElement(element);
			element.click();
			LOGGER.info("Button clicked.");
		}
		
	//Method to perform a click after element is loaded - parameter is xpath of String type
		public void xpathClick(String xpath) throws InterruptedException
		{
			waitForClickableDynamicElement(By.xpath(xpath));
			driver.findElement(By.xpath(xpath)).click();
			LOGGER.info("Button clicked.");
			pause(2000);	
		}
	
		
	//Method to perform javascript click
		public void javaClick(WebElement element) throws InterruptedException
		{
			waitForClickableElement(element);
			JavascriptExecutor executor1 = (JavascriptExecutor)driver;
			executor1.executeScript("arguments[0].click();", element);
			LOGGER.info("Performed java click.");
		}
		
	//Method to perform javascript click - parameter is xpath of String type
		public void javaXpathClick(String xpath) throws InterruptedException
		{
			waitForClickableDynamicElement(By.xpath(xpath));
			JavascriptExecutor executor1 = (JavascriptExecutor)driver;
			executor1.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath)));
			LOGGER.info("Performed java click.");
		}

		
		
	//Method to perform sendkeys - parameter is page factory element
		public void sendKeys(WebElement element, String key) throws InterruptedException
		{
			waitForClickableElement(element);	
			element.clear();
			element.sendKeys(key);
		}
		
	//Method to perform sendkeys - parameter is xpath of String type
		public void xpathSendKeys(String xpath, String key) throws InterruptedException
		{
			waitForClickableDynamicElement(By.xpath(xpath));	
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(key);
			LOGGER.info("Input added: "+key);
		}


	
	//This method is to switch to a new tab. Caller should supply element to click. (@findby as parameter)
	public void switchToNewTab(WebElement element) throws InterruptedException
	{
		String currentHandle= driver.getWindowHandle();
		
		click(element);
		
		//Get all the handles currently available
    	Set<String> handles=driver.getWindowHandles();
    	
    	for(String actual: handles) 
    	{
	    	if(!actual.equalsIgnoreCase(currentHandle)) 
	    	{
		    	//Switch to the opened tab
		    	driver.switchTo().window(actual);
		    	LOGGER.info("Switched to new tab.");
	    	}
    	}
	}
	
	
	
	//This method is to switch to a new tab. Caller should supply element to click. (@xpath as parameter)
	public void switchToNewTab(String xpath) throws InterruptedException
	{
		String currentHandle= driver.getWindowHandle();
		
		driver.findElement(By.xpath(xpath)).click();
		
		//Get all the handles currently available
    	Set<String> handles=driver.getWindowHandles();
    	
    	for(String actual: handles) 
    	{
	    	if(!actual.equalsIgnoreCase(currentHandle)) 
	    	{
		    	//Switch to the opened tab
		    	driver.switchTo().window(actual);
		    	LOGGER.info("Switched to new tab.");
	    	}
    	}
	}
	
		
}