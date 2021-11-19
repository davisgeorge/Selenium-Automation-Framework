package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPageObject extends Page
{
	
	public static final Logger LOGGER = LogManager.getLogger(SearchPageObject.class);

    
    public SearchPageObject(WebDriver driver) 
    {
		super(driver);
		// TODO Auto-generated constructor stub
	}


   //Get search result header
    @FindBy(xpath = "//h1[contains(@class,'Title-sc')]")
    WebElement searchResultHeader;

	
    
	
   //Method to get search page header
    public String getSearchPageHeader() throws InterruptedException
    {
    	waitForElementVisbility(searchResultHeader); 
      String headerText = searchResultHeader.getText();
      return headerText;
	 }    
    
}
