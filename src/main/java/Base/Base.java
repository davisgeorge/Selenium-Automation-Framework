package Base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base 
{
	public static WebDriver driver;
	
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	
	 public static Config settings;
	  
	 public static String testName;
	
	  @Parameters({"headless"})
	  @BeforeSuite
	  public WebDriver setup(String headless) throws IOException
	  {
		  
	        WebDriverManager.chromedriver().setup();
	        
	        ChromeOptions options = new ChromeOptions();
	        
	        if(headless.equalsIgnoreCase("yes"))
            {
              System.out.println("Browser will run in headless mode.");
              options.addArguments("--headless");
            }
            else
            {
              System.out.println("Browser will run in headed mode.");
            }
	        
	        driver = new ChromeDriver(options);
	        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
	    	driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
	    	driver.manage().window().maximize();

	    	//set driver to threadDriver so that we can reference the instance of same driver whereever required via getDriver method

	    	threadDriver.set(driver);
	    	return getDriver();
	    }
	  
	  public static synchronized WebDriver getDriver() 
	  {
			return threadDriver.get();
	  }
	  
	  //Method to get the name of the test in execution
	  @BeforeMethod
	  public void getTestDetails(ITestContext testNameFetcher, Method method)
	  {
			final String xmlTestName = testNameFetcher.getName();
			final String methodName = method.getName();
		    testName = xmlTestName + "." + methodName;
		    
		    System.out.println("Test to run is: "+testName);
		    
	  }

	  	//Method to close the browser after test execution
		@AfterSuite
		public void closeBrowser()
		{
			 driver.close();
		}
		
		//Method to take UI screenshot when a test fails.
		public void takeScreenshot()
		{
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");  
			 LocalDateTime now = LocalDateTime.now();  
			
			String filepath;
			
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			
		       if(System.getProperty("os.name").contains("Windows"))
		    	   filepath = System.getProperty("user.dir")+"\\Screenshots";
	              else if(System.getProperty("os.name").contains("Mac"))
	            	  filepath = System.getProperty("user.dir")+"/Screenshots";
	              else
	            	  filepath = "./Screenshots";
			
			
			try 
			{
				FileUtils.copyFile(srcFile, new File(filepath+dtf.format(now)+testName+"_"+".jpg"));
				System.out.println("Screenshot taken.");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
}
