package com.qa.OrangeHRM.factory;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;
	
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	private static final Logger log=LogManager.getLogger(OptionsManager.class);
	
	//created initprop method in driverFactory and using prop ref here so this is call by ref
	public OptionsManager(Properties prop) {
		this.prop=prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co=new ChromeOptions();
		//headless is string so convert into boolean
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			//System.out.println("***************Running in Headless mode*************");
			log.info("**********Running in Headless mode********");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			//System.out.println("***************Running in Incognito mode*************");
			log.info("**********Running in InCognito mode********");
			co.addArguments("--incognito");
		}
		return co;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo=new EdgeOptions();
		//headless is string so convert into boolean
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("***************Running in Headless mode*************");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("***************Running in Incongnito mode*************");
			eo.addArguments("--inPrivate");
		}
		return eo;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo=new FirefoxOptions();
		//headless is string so convert into boolean
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("***************Running in Headless mode*************");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("***************Running in Incognito mode*************");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	
	
	
	
	

}
