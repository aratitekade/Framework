package com.qa.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.OrangeHRM.util.ElementUtil;

public class HomePage {
	
private WebDriver driver;
private ElementUtil eleUtil;
	
	//to access private driver
	public HomePage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//1.locators
	private By upgrade_btn=By.xpath("//*[local-name()='svg' and @class='oxd-icon orangehrm-upgrade-icon']/parent::button[@type='button']");
	private By header=By.xpath("//h6[text()='Dashboard']");
	private By search=By.xpath("//input[@placeholder='Search']");
	//private By leave_tab=By.xpath("//span[text()='Leave']");
	private By leave_tab=By.xpath("//ul[@class='oxd-main-menu']//li//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name']");
	
	
	//2.public methods/page actions
		public String getHomePageTitle() {
			String title=driver.getTitle();
			System.out.println("Home Page Title:"+title);
			return title;
		}
		
		public String getHomePageURL() {
			String url=driver.getCurrentUrl();
			System.out.println("Home Page URL:"+url);
			return url;
		}
		
		public boolean isupgradeBtnExist() {
			return driver.findElement(upgrade_btn).isDisplayed();
			
			}
		
		public String getHeader() {
		 return driver.findElement(header).getText();
			
		}
		
		public SearchResultsPage doSearch(String searchkey) {
			System.out.println("Search Key:"+searchkey);
			driver.findElement(search).sendKeys(searchkey);
			driver.findElement(leave_tab).click();
			return new SearchResultsPage(driver);
		}

}
