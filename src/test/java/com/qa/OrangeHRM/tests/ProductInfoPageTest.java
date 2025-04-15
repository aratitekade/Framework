package com.qa.OrangeHRM.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.OrangeHRM.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		homePage=loginPage.doLogin("Admin", "admin123");
	}
	
//	@DataProvider
//	public Object[][] getButtonInfo() {
//		return new Object[][] {
//			{"Admin","System Users"},
//			{"PIM","Employee Information"},
//			{"Leave","Leave List"},
//			{"Time","Select Employee"}			
//		};
//		
//	}
//	
//	@Test(dataProvider="getButtonInfo")
//	public void fieldSearchHeaderTest(String searchKey,String Tab_name) {
//		searchResultsPage=homePage.doSearch(searchKey);
//		productInfoPage = searchResultsPage.selectButton(Tab_name);
//		String actualFieldHeader=productInfoPage.getFieldHeader();
//		Assert.assertEquals(actualFieldHeader, Tab_name);
//		}
	
	@Test
	public void fieldSearchHeaderTest() {
		searchResultsPage=homePage.doSearch("Leave");
		productInfoPage = searchResultsPage.selectButton("Leave");
		String actualFieldHeader=productInfoPage.getFieldHeader();
		Assert.assertEquals(actualFieldHeader, "Leave List");
		}
	
	

}
