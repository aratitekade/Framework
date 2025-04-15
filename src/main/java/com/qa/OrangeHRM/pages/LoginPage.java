package com.qa.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	
	//to access private driver
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
	
	//1.maintain By locators
	private By username=By.name("username");
	private By password=By.name("password");
	private By login_btn=By.xpath("//button[@type='submit']");
	private By forgotpwdLink=By.xpath("//p[text()='Forgot your password? ']");
	
	
	//2.public methods/page actions
	@Step("getLoginPageTitle")
	public String getLoginPageTitle() {
		String title=driver.getTitle();
		System.out.println("Login Page Title:"+title);
		ChainTestListener.log("Login Page Title using ChainTestListener:"+title);
		return title;
	}
	
	@Step("getLoginPageURL")
	public String getLoginPageURL() {
		String url=driver.getCurrentUrl();
		System.out.println("Login Page URL:"+url);
		return url;
	}
	
	@Step("Checking forgot pwd link is displayed")
	public boolean isForgotPwdLinkExist() {
		return driver.findElement(forgotpwdLink).isDisplayed();
		
		}
	@Step("Login using username: {0} and password : {1}")
	public HomePage doLogin(String un,String pwd) {
		System.out.println("App credentials are:" +un +" "+ pwd);
		driver.findElement(username).sendKeys(un);
		driver.findElement(password).sendKeys(pwd);
		driver.findElement(login_btn).click();
		return new HomePage(driver); 
		
		
	}
	
	

}
