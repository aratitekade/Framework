package com.qa.OrangeHRM.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.OrangeHRM.factory.DriverFactory;

public class ElementUtil {
	// all generic methods

	static WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil=new JavaScriptUtil(driver);
	}

	// for single element:diver.findElement()
	//here we are using highlight property if it is true
	
	public WebElement getElement(By locator) {
		WebElement element=driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element; 
	}

	// in sendKeys ,we passes CharSequence... value it acts as array means we can
	// pass
	// string ,StringBuffer and StringBuilder
	// doSendKeys
	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(WebElement element, CharSequence... value) {
		nullCheck(value);
		element.clear();
		element.sendKeys(value);
	}

	// overloaded method
	public void doSendKeys(String locatorType, String locatorValue, CharSequence... value) {
		nullCheck(value);
		getElement(getLocator(locatorType, locatorValue)).sendKeys(value);
	}

	// we cannot pass null in sendKeys:it gives IllegalArgumentException
	// if someone passes so creating utility and passing own exception
	private void nullCheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("---------value/prop_name/attr_name cannot be null----------");
		}
	}

	public void closeBrowser() {
		driver.quit();
	}

	// doClick
	public void doClick(By locator) {
		getElement(locator).click();
	}

	// overloaded doclick method
	public void doClick(String locatorType, String locatorValue) {
		getElement(getLocator(locatorType, locatorValue)).click();
	}

	// doElementGetText
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(getLocator(locatorType, locatorValue)).getText();
	}

	// isDisplayed() method will work for single element. we created this utility if
	// element is not avaible on the
	// page then it gives NSE(NoSuchElementException) using this utility it gives
	// msg and return false
	public boolean doIsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed");
			return false;
		}
	}

	// for driver.findelements()
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// another option for isDisplayed()method -but for single element
	public boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("element is available on the page one time");
			return true;
		} else {
			System.out.println("element is not available on the page");
			return false;
		}
	}

	// isDisplayed()method work for single element but this utility will work for
	// multiple elements
	// eg :in naveenautomationlabs login page, forgotten password is present 2 times
	public boolean isElementDisplayed(By locator, int elementCount) {
		if (getElements(locator).size() == elementCount) {
			System.out.println("element is available on the page " + elementCount + " times");
			return true;
		} else {
			System.out.println("element is not available on the page");
			return false;
		}
	}

	public String doGetDomAttribute(By locator, String attrName) {
		nullCheck(attrName);// noone can pass null if pass it throw exception
		return getElement(locator).getDomAttribute(attrName);
	}

	public String doGetDomProperty(By locator, String propName) {
		nullCheck(propName);// noone can pass null if pass it throw exception
		return getElement(locator).getDomProperty(propName);
	}

	// Select tag dropdown utility (no need to create select class object multiple
	// times)
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(5);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	// new method got added
	public void doSelectDropDownByContainsText(By locator, String partialText) {
		Select select = new Select(getElement(locator));
		select.selectByContainsVisibleText(partialText);
	}

	// printDropDownOptionsText: is used only to print
	public void printDropDownOptionsText(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> OptionsList = select.getOptions();
		System.out.println("Size:" + OptionsList);

		for (WebElement e : OptionsList) {
			String text = e.getText();
			System.out.println(text);
		}

	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> OptionsList = select.getOptions();
		System.out.println("Options size id:" + OptionsList.size());// 233

		List<String> OptionsValueList = new ArrayList<String>();// we created blank array list means we get all option
																// in form of array like
		// [option1,option2,..........option n]
		for (WebElement e : OptionsList) {
			String text = e.getText();
			OptionsValueList.add(text);
		}
		return OptionsValueList;
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));

		List<WebElement> countryOptionsList = select.getOptions();
		System.out.println("Options size id:" + countryOptionsList.size());// 233
		return countryOptionsList.size();

	}

	// utility if we don't want to use 3 select class
	// methods(selectByIndex,selectByVisibleText,selectByValue)
	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions(); // ctrl+2 for assigning variable i.e List<WebElement>
		boolean flag = false;// for incorrect country name

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(value + " is available and selected");
		} else {
			System.out.println(value + " is not available and selected");
		}
	}

	// utility for search:google search
	public void doSearch(By searchField, By suggestions, String searchKey, String actualValue)
			throws InterruptedException {

		// getElement(searchField).sendKeys(searchKey);
		doSendKeys(searchField, searchKey);
		Thread.sleep(2000);

		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());// 10
		boolean flag = false;

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);

			if (text.contains(actualValue)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(actualValue + "is available and clicked");
		} else {
			System.out.println(actualValue + "is not available");
		}

	}

	/**
	 * this method is handling single,multiple and all choices selection.Please pass
	 * "all" to select all the choices. selectChoice( choiceDropDown,choices,
	 * "all");
	 * 
	 * @param choiceDropDown
	 * @param choices
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choiceDropDown, By choices, String... choiceValue) throws InterruptedException {

		// driver.findElement(choiceDropDown).click();
		// we have doClick utility so we can use it

		doClick(choiceDropDown);
		Thread.sleep(3000);

		// List<WebElement> choicesList = driver.findElements(choices);
		// instead of driver.findElements method we need to write getelements method we
		// created

		List<WebElement> choicesList = getElements(choices);
		System.out.println(choicesList.size());

		if (choiceValue[0].equalsIgnoreCase("all")) {
			// select all choice one by one
			for (WebElement e : choicesList) {
				e.click();
			}
		}

		else {
			for (WebElement e : choicesList) {
				String text = e.getText();
				System.out.println(text);

				for (String ch : choiceValue) {
					if (text.equals(ch)) {
						e.click();
					}
				}

			}
		}

	}

	// Actions class utilities

	public void doActionsSendKeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	// eg:spicejet.com
	public void handleTwoLevelMenuSubMenuHandling(By parentMenuLocator, By childMenuLocator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		getElement(childMenuLocator).click();

	}

	// eg.bigbasket
	public void handleFourLevelMenuSubMenuHandling(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		Actions act = new Actions(driver);
		driver.findElement(level1Menu).click();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		driver.findElement(level4Menu).click();

	}
	
	// ***********************wait for single element Utils***************
	/**
	 * presenceOfElementLocated:An expectation for checking that an element is
	 * present on the DOM of a page. This does notnecessarily mean that the element
	 * is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	//adding polling time 
	public WebElement waitForElementPresence(By locator, long timeOut,long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// instead of waitForElementPresence we can use waitForElementVisible
	//visibilityOfElementLocated:
	// An expectation for checking that an element is present on the DOM of a page and visible.
	// Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	public WebElement waitForElementVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	//****************Webdriver interface has one overloaded constructor with sleep/polling time*************************
	public WebElement waitForElementVisible(By locator, long timeOut,long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	//**************wait for multiple webelements***********************************************
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  List<WebElement> waitForElementsPresence(By locator,long timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		catch(TimeoutException e) {
			return Collections.EMPTY_LIST;
		}
		
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  List<WebElement> waitForElementsVisible(By locator,long timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(TimeoutException e) {
			return Collections.EMPTY_LIST;
		}
		
		
	}
	
	
	//elementToBeClickable:An expectation for checking an element is visible and enabled such that you can click it.
	public WebElement clickElementWhenReady(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	
	
	//******************title utility************************
	public  String waitForTitleContains(String fractionTitle, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeOut + " seconds");
		}

		return null;

	}

	public  String waitForTitleIs(String title, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeOut + " seconds");
		}

		return null;

	}
	
	//*******************URL utility****************************
	//for partial URL
		public  String waitForURLContains(String fractionURL,long timeOut) {
			
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			
			try {
			if( wait.until(ExpectedConditions.urlContains(fractionURL))) {
				 return driver.getCurrentUrl();
			  }	
			}catch(TimeoutException e) {
				System.out.println("URL is not found after " + timeOut + " seconds");
				
			}
			return null;
			}
		
		//for exact URL
		public  String waitForURLToBe(String URL,long timeOut) {
			
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			
			try {
			if( wait.until(ExpectedConditions.urlToBe(URL))) {
				 return driver.getCurrentUrl();
			  }	
			}catch(TimeoutException e) {
				System.out.println("URL is not found after " + timeOut + " seconds");
				
			}
			return null;
			}
		
		//***********************Alert utility******************************************
		public  Alert waitForAlert(long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public  String getAlertText(long timeOut) {
			return waitForAlert(timeOut).getText();
		}
		
		public  void acceptAlert(long timeOut) {
			waitForAlert(timeOut).accept();
		}
		
		public  void dismissAlert(long timeOut) {
			waitForAlert(timeOut).dismiss();
		}
		
		public  void alertSendKeys(String text,long timeOut) {
			 waitForAlert(timeOut).sendKeys(text);
		}
		
		//************************Frame Utils*******************************
		
		public  void waitForFrameByLocatorAndswitchToit(By frameLocator,long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		public  void waitForFrameByIndexAndswitchToit(int frameIndex,long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		public  void waitForFrameByLocatorAndswitchToit(String frameIDorname,long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDorname));
		}
		
		public  void waitForFrameByLocatorAndswitchToit(WebElement frameElement,long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}
		
		//***********************wait for browser window*******************************************
		public  boolean waitForWindow(int numberOfWindows,long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
			}
			catch(TimeoutException e) {
				System.out.println("number of windows not matched");
				return false;
			}
		}
		
		//******************page loading utility*****************************
		public  boolean isPageLoaded(long timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			String isPageLoaded=wait.until(ExpectedConditions.jsReturnsValue("return document.readyState;")).toString();
			return Boolean.parseBoolean(isPageLoaded);
		}
		
		//*************fluentWait*********************
		public  WebElement waitforElementVisibleUsingFluentFeatures(By locator,long timeOut,long pollingTime) {
			FluentWait<WebDriver> wait=new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(pollingTime))
					.ignoring(NoSuchElementException.class)
					.withMessage("element is not found");
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}

	// here locatorType-ID,name ..etc
	public By getLocator(String locatorType, String locatorValue) {

		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;

		default:
			System.out.println("invalid locator, please use the right locator...");
			break;
		}

		return locator;

	}

}

