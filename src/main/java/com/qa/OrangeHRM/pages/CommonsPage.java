package com.qa.OrangeHRM.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.OrangeHRM.util.ElementUtil;

public class CommonsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public CommonsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By logo=By.xpath("//img[@alt='client brand banner']");
	private By footer=By.xpath("//div[@class='oxd-layout-footer']/p");
	
	public   boolean IsLogoDisplayed() {
		//return eleUtil.doIsElementDisplayed(logo);
		return driver.findElement(logo).isDisplayed();
	}
	
	public List<String> getFooterText() {
		System.out.println("****Footer Text on Page****");
		List<WebElement> footerText = driver.findElements(footer);
		List<String> footers=new ArrayList<String>();
		for(WebElement e:footerText) {
			String text=e.getText();
			footers.add(text);
		}
		return footers;
}
	}
