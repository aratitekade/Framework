package com.qa.OrangeHRM.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.OrangeHRM.base.BaseTest;
import com.qa.OrangeHRM.constants.AppConstants;
import com.qa.OrangeHRM.constants.AppError;
import com.qa.OrangeHRM.util.ExcelUtil;

public class HomePageTest extends BaseTest {
	
	@BeforeClass
	public void homePageSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void HomePageTitleTest() {
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	@Test
	public void HomePageURLTest() {
		String actURL=homePage.getHomePageURL();
		Assert.assertTrue(actURL.contains(AppConstants.HOME_PAGE_URL_FRACTION),AppError.URL_NOT_FOUND_ERROR);
	}
	
	@Test
	public void isupgradeBtnExistTest() {
		Assert.assertTrue(homePage.isupgradeBtnExist(), "Button not Exist");
	}
	
	@Test
	public void headerTest() {
		String hea=homePage.getHeader();
		System.out.println("HomePage header:"+hea);
	}
	
	@DataProvider
	public Object[][] getButtonListData() {
		return new Object[][] {
			{"Admin",7},
			{"PIM",4},
			{"Leave",7},
			{"Time",4},
			{"Recruitment",2},
			{"Performance",4}
			
		};
		
	}
	
	//data provider for excel sheet
	@DataProvider
	public Object[][] getButtonListSheetData() {
		Object productData[][]=ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		return productData;
	}
	
	//method for for excel sheet
//	@Test(dataProvider="getButtonListSheetData") 
//	public void searchTest(String searchKey,String resultCount) {
//		searchResultsPage=homePage.doSearch(searchKey);
//		Assert.assertEquals(searchResultsPage.getNavLinksAvailableOnPage(), Integer.parseInt(resultCount));
//	}
	
	@Test(dataProvider="getButtonListData")
	public void searchTest(String searchKey,int resultCount) {
		ChainTestListener.log(searchKey +" : " +resultCount);
		searchResultsPage=homePage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getNavLinksAvailableOnPage(), resultCount);
	}
	
	@Test
	public void logoTest() {
		Assert.assertTrue(commonspage.IsLogoDisplayed(),AppError.LOGO_NOT_FOUND_ERROR);
	}
	
	@Test
	public void FooterTest() {
		List<String> footers=commonspage.getFooterText();
		Assert.assertTrue(footers.contains("OrangeHRM OS 5.7"), AppError.LOGO_NOT_FOUND_ERROR);
	}

}
