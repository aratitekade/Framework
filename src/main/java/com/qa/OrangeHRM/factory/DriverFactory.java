package com.qa.OrangeHRM.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;


import com.qa.OrangeHRM.constants.AppConstants;
import com.qa.OrangeHRM.exception.FrameworkException;

import io.qameta.allure.Description;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	//ThreadLocal<WebDriver>:when we generates thread then it creates local copy of driver
	
	private static final Logger log=LogManager.getLogger(DriverFactory.class);
	
	@Description("Initialize the driver uisng properties: {0}")
	public WebDriver initDriver(Properties prop) {
		
		String browserName=prop.getProperty("browser");
		//prop.getProperty("browser").trim().toLowerCase();
		
		//instead of system.out.println we can use log methods as we added log4j dependency and  prop file
		//System.out.println("Browser name is:"+browserName);
		log.info( "Browser name is:" +browserName);
		
		highlight=prop.getProperty("highlight");
		
		optionsManager=new OptionsManager(prop);
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			//driver= new ChromeDriver(optionsManager.getChromeOptions());
			break;
			
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			//driver= new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
			
		case "Edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			//driver= new EdgeDriver(optionsManager.getEdgeOptions());
			break;
			
		case "safari":
			tlDriver.set(new SafariDriver());
			//driver= new SafariDriver();
			break;

		default:
			//System.out.println("please pass right browser name.." +browserName);
			log.error("please pass right browser name.." +browserName);
			throw new FrameworkException("---invalid browser name---");
			
		}
		//now we need to use getDriver() method
		//driver.manage().deleteAllCookies();
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		//driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		return getDriver();
	}
	
	/**
	 * This method is used to get driver using ThreadLocal
	 * @return 
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/**
	 * This method is used to init the properties from .properties file
	 * @return
	 */
	
	
	//supply env name using maven command line
	//command: mvn clean install -Denv="qa"
	public Properties initProp() {
		
		//to read envname using maven use method system.getProperty
		String envName=System.getProperty("env");
		
		//System.out.println("Running Test Suite On Env: "+envName);
		log.info("Running Test Suite On Env: "+envName);
		FileInputStream ip=null;
		prop=new Properties();
		
		
		try {
		if(envName==null) {
			//System.out.println("No Env is passed, hence Running Test Suite on QA env");
			log.warn("No Env is passed, hence Running Test Suite on QA env");
			ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
		}
		else {
			switch (envName.trim().toLowerCase()) {
			case "qa":
				ip=new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
				break;
				
			case "dev":
				ip=new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
				break;
				
			case "prod":
				ip=new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);
				break;
						
			default:
				throw new FrameworkException("----Invalid Environment Name Passed----");
			}
	  }
		prop.load(ip);
	}
		catch (FileNotFoundException e) {
			//e.printStackTrace();
			log.error(e.getMessage());//we can write instead of e.printstackTrace()
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	/**
	 * takescreenshot:when test get passed/failed then only takes screenshot
	 * TakesScreenshot is interface and getScreenshotAs is method
	 * user.dir:goes to project/root dir and for unique name for screenshot attaching _ with current time in ms
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// screenshot create in temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	//getScreenshot() gives String path but we need srcfile path to take screenshot for embed(File file,String mimitype) 
	//method.(MIME type means supported image type like png,jpg etc)so creating another method
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}
}
	
	
	


