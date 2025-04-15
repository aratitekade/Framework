package com.qa.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.OrangeHRM.util.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By Navlinks=By.xpath("//div[@class='oxd-topbar-body']//li");
	
	public int getNavLinksAvailableOnPage() {
		int resultCount= driver.findElements(Navlinks).size();
		System.out.println("NavBar Links size:"+resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectButton(String fieldName) {
		System.out.println("Select Nav Button:" +fieldName);
		driver.findElement(By.linkText(fieldName)).click();
		return new ProductInfoPage(driver);
	}

}
