package com.perfecto.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.perfecto.baseclass.CommonComponents;
import com.perfecto.utility.ComplexReportFactory;
import com.perfecto.utility.ExcelReader;
import com.perfecto.utility.TestCaseBase;

public class TC001_Verify_Google_Search_Page extends TestCaseBase {
		
	String env;

	@DataProvider
	public Object[][] get_Data_TC001_Verify_Google_Search_Page()
	{
		return ExcelReader.getTestData("google.xlsx","googleSearchFunctionality","TC001_Verify_Google_Search_Page");
	}

	@Test(dataProvider="get_Data_TC001_Verify_Google_Search_Page",priority =0, groups= {"desktop", "mobile"})
	public void homePage(Hashtable<String,String> dataTable) throws Exception {

		CommonComponents comp = new CommonComponents(pageManager, excelReader);

		env = environment;
		comp.open("googleHomePage", env);
		
		pageManager.waitForSeconds(3000);
		pageManager.refresh();
		
		pageManager.waitForSeconds(3000);
		pageManager.refresh();
		
		pageManager.waitForSeconds(3000);
		pageManager.refresh();

		pageManager.logToReport("Verifying Homepage page title is: " + dataTable.get("SearchPageTitle"));
		customAssertion.assertTrue(pageManager.getTitle().contains(dataTable.get("SearchPageTitle")));

		Assert.assertEquals(ComplexReportFactory.getTest(testName).getStatus(), Status.PASS);
	}
}
