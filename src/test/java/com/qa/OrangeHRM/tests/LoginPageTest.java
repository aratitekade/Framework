package com.qa.OrangeHRM.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.OrangeHRM.base.BaseTest;
import com.qa.OrangeHRM.constants.AppConstants;
import com.qa.OrangeHRM.constants.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Login Page for OrangeHRM")
@Story("US 101:Design various features of Login Page for OrangeHRM")
@Feature("Feature 50:Login level feature")
@Owner("Arati Nitin Kale")
public class LoginPageTest extends BaseTest{
	
	@Description("checking Login Page Title..")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("Verifying Login Page Title");
		String actTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE,AppError.TITLE_NOT_FOUND_ERROR);
	}
	//@Test(enabled=false) means this testcase will not exceute
	@Description("checking Login Page URL..")
	@Severity(SeverityLevel.MINOR)
	@Test(description="checking Login Page Title")
	public void loginPageURLTest() {
		String actURL=loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION),AppError.URL_NOT_FOUND_ERROR);
	}
	
	@Description("checking Forgot Pwd Link Exist or not")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	@Description("checking User is able to Login with right credentials..")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void loginTest() {
		homePage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), "OrangeHRM","title is not matched");
	}
	
	
	
}
