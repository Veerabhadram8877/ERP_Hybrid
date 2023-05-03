package commonFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static String Actual="";
	public static String Expected="";
	//method for launch browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser value is not matching");
		}
		return driver;
	}
		
	//method for launching url
	public static void openUrl(WebDriver driver)
	{
	driver.get(conpro.getProperty("Url"));	
	}
	// method for wait for element
	public static void waitforElement(WebDriver driver,String LocatorType,String LocatorValue,String waitTime)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Integer.parseInt(waitTime));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
	}
	//method for textboxes
	public static  void typeAction(WebDriver driver,String Locatortype,String LocatorValue,String TestData)
	{
		if(Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
			else if(Locatortype.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(LocatorValue)).clear();
				driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
			}
			else if(Locatortype.equalsIgnoreCase("xpath"))
			{
				driver.findElement(By.xpath(LocatorValue)).clear();
				driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
			}
 
			}
	//method for bottons,radio buttons,links,images,chechboxes
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	//method for validate title
	public static void validateTitle(WebDriver driver,String ExpectedTitle)
	{
		String ActualTitle = driver.getTitle();
		try {
		Assert.assertEquals(ExpectedTitle, ActualTitle,"Title is not Matching");
		} catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Categories')])[2]"))).click().perform();
	}
	//method for stocktable
	public static void categoryTable(WebDriver driver,String ExpectedData) throws Throwable
	{
	//if search box is already displayed no need to tuch search panel
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		    driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(ExpectedData);
		    Thread.sleep(3000);
		    driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		    String Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		    System.out.println(ExpectedData+"   "+Actualdata);
		    Assert.assertEquals(ExpectedData, Actualdata,"when category name not matching");
			
		
	}
	public static void capureSnumber(WebDriver driver,String LocatorType,String LocatorValue)
	{
		Expected = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		    driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
		    Thread.sleep(3000);
		    driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		    Actual = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		    System.out.println(Expected+"   "+Actual);
		    Assert.assertEquals(Expected, Actual, "supplier number not matched");
		    
	}
	public static void captureCnumber(WebDriver driver,String LocatorType,String LocatorValue)
	{
		Expected = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	public static void customerTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		    driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
		    Thread.sleep(3000);
		    driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		    Actual = driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
		    System.out.println(Expected+"   "+Actual);
		    Assert.assertEquals(Expected, Actual, "customer number is not displayed");
	}
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy_mm_dd hh_mm");
		return df.format(date);
	}
}
		
	


