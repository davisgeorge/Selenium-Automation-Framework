

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.Base;
import Base.Config;
import PageObjects.HomePageObject;
import PageObjects.Page;
import PageObjects.SearchPageObject;
import Utilities.CustomListener;
import Utilities.ExcelControlCenter;



@Listeners(CustomListener.class)
public class TestGiphy extends Base
{
	
	//This is a class to test Giphy website
	
	private static final Logger LOGGER = LogManager.getLogger(TestGiphy.class);
	
	
	  //DATA PROVIDER to fetch values from excel
	
	@DataProvider(name = "Giphy")
	public Object[][] getData() throws IOException
	{
			
	        String sheetName = "Online Purchase";
			ExcelControlCenter ex = new ExcelControlCenter("TestData",sheetName);
			Object data[][] = ex.getTestData(sheetName);
			return data;
	}

	    
	@Test(dataProvider = "Giphy")
    public static void testGiphyWebsite(String dataValue) throws Exception
    {
		//Import page objects
		HomePageObject hp = PageFactory.initElements(driver, HomePageObject.class);
		SearchPageObject sp = PageFactory.initElements(driver, SearchPageObject.class);
		
		//Get url from config file
    	LOGGER.info("Open Giphy URL");
		String url = Config.getInstance().getString("giphyUrl");

		driver.get(url);
		LOGGER.info("Opened Giphy URL");

		Assert.assertEquals(driver.getTitle(), "GIPHY - Be Animated");

		LOGGER.info("TC_01 : Verify Trending section exists");
		hp.verifyTrendingSectionExists();
		LOGGER.info("TEST PASSED!");

		LOGGER.info("TC_02 : Verify search field exists.");
		hp.verifySearchBarExists();
		LOGGER.info("TEST PASSED!");

		LOGGER.info("TC_03: Verify user can type text into search field.");
		LOGGER.info("Search keyword from excel is: "+dataValue);
		hp.enterSearchText(dataValue);
		LOGGER.info("TEST PASSED!");

		LOGGER.info("TC_04 : Perform search.");
		hp.clickOnSearchButton();
		LOGGER.info("TEST PASSED!");

		LOGGER.info("TC_05 : Verify results are displayed after a search.");
		hp.clickOnSearchButton();
		LOGGER.info("TEST PASSED!");

		LOGGER.info("TC_06 : Verify results are displayed after a search.");
		String searchResult = sp.getSearchPageHeader();
		Assert.assertEquals(searchResult, dataValue);
		LOGGER.info("TEST PASSED!");
    
    }
	
			  
}
