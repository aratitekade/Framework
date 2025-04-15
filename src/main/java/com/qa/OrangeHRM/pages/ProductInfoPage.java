package com.qa.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductInfoPage {
	
	private WebDriver driver;

	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
	}
	
	private By fieldHeader=By.tagName("h5");
	
	public String getFieldHeader() {
		String header=driver.findElement(fieldHeader).getText();
		System.out.println("Field Header:" +header);
		return header;
	}

}
