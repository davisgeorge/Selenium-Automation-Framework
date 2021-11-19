package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageObject extends Page
{
	
	public static final Logger LOGGER = LogManager.getLogger(HomePageObject.class);

    
    public HomePageObject(WebDriver driver) 
    {
		super(driver);
		// TODO Auto-generated constructor stub
	}


   //Trending section
    @FindBy(xpath = "//h3[text()='Trending']")
    WebElement searchBoxtrendingSection;

	//Search box
	@FindBy(xpath = "//div[@id='searchbar']")
	WebElement searchBox;

	//Search box input
	@FindBy(xpath = "//div[@id='searchbar']//input")
	WebElement searchBoxInput;

	//Search button
	@FindBy(xpath = "//a[contains(@class,'SearchButton')]")
	WebElement searchButton;
	
    
	
 //Method to verify trending section exists
 public void verifyTrendingSectionExists() throws InterruptedException
 {
	 waitForElementVisbility(searchBoxtrendingSection) ; 
   	 LOGGER.info("Trending section exists.");
 }  
 
 	
 //Method to verify search bar exists
 public void verifySearchBarExists() throws InterruptedException
 {
	 waitForElementVisbility(searchBox) ; 
   	 LOGGER.info("Search bar exists.");
 }    

 //Method to enter search text
 public void enterSearchText(String searchText) throws InterruptedException
 {
	 waitForElementVisbility(searchBoxInput) ; 
	 sendKeys(searchBoxInput, searchText);
   	 LOGGER.info("Entered search text.");
 }   
 
  //Method to click on search button
  public void clickOnSearchButton() throws InterruptedException
  {
	 	 waitForElementVisbility(searchButton);
		  click(searchButton); 
		 LOGGER.info("Clicked on search button.");
  }    
    
}
