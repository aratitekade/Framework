package com.qa.OrangeHRM.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.beust.jcommander.Parameters;
import com.qa.OrangeHRM.factory.DriverFactory;
import com.qa.OrangeHRM.pages.CommonsPage;
import com.qa.OrangeHRM.pages.HomePage;
import com.qa.OrangeHRM.pages.LoginPage;
import com.qa.OrangeHRM.pages.ProductInfoPage;
import com.qa.OrangeHRM.pages.SearchResultsPage;

import io.qameta.allure.Description;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	 WebDriver driver;
	//in driverFactory we already initialized a driver so create object
	DriverFactory df;
	
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonspage;

  //@Parameters({"browser"}) in testng if we pass parameter as browser
	@BeforeTest(description="In Setup method, initialiaze the driver and properties..")
	public void setup(String browserName) {
		df=new DriverFactory();
		prop=df.initProp();
		//prop.getProperty("browser");instead of writing this user propertis ref in initDriver that is call by ref 
		
		//this code is for :if we want pass parameter as browserName via testng xml
		if(browserName!=null) {
			prop.setProperty("browser", browserName);//here it will update browserName(config.properties) to browser 
			//value which we pass in testng xml.(<parameter name="browser" value="chrome" />)
		}
		driver=df.initDriver(prop);
		loginPage=new LoginPage(driver);
		commonspage=new CommonsPage(driver);
		
		//to add system variable in chaintest report
		ChainPluginService.getInstance().addSystemInfo("Headless#",prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("Owner#",prop.getProperty("Arati Nitin Kale"));
		
	}
	
	//takes screenshot for passed/fail testcases
	@AfterMethod(description="In attachScreenshot method,taking screenshot of testcases..")
	public void attachScreenshot() {
		ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
	}
	
	
	//if you want to take screenshot only for fail testacse then use this method
//	@AfterMethod
//	public void attachScreenshot(ITestResult result) {
//		if(!result.isSuccess()) {
//		ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
//		}
//	}
	
	
	@AfterTest(description="In tearDown method,closing the browser")
	public void tearDown() {
		driver.quit();
	}
}
