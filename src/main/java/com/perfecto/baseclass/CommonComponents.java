package com.perfecto.baseclass;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.perfecto.utility.ExcelReader;
import com.perfecto.utility.PageManager;
import com.perfecto.utility.PageObject;
import com.perfecto.utility.PostJsonObject;
import com.perfecto.utility.TestData;


public class CommonComponents extends PageObject {
	
	ArrayList<String> boostURLs ;
	//ArrayList beconGuest;

	public CommonComponents(PageManager pm, ExcelReader excelReader) {
		super(pm, excelReader);
	}
	
	public void loadURLs(String url)
	{
		String fileName = "testdata_urls.properties";
		TestData.loadURLs(fileName);
		
		if(url.equalsIgnoreCase("googleHomePage"))
		{
			String googleHomePageTest  = TestData.getURLs("googleHomePageTest");
			String googleHomePageQA  = TestData.getURLs("googleHomePageQA");
			String googleHomePageDev  = TestData.getURLs("googleHomePageDev");
			 
			boostURLs = new ArrayList<String>();
			boostURLs.add(googleHomePageTest);
			boostURLs.add(googleHomePageQA);
			boostURLs.add(googleHomePageDev);
		}
		
	}

	

	/**
	 * @Description: This Method opens the URL
	 * @Author: Anushree Kharwade
	 * @CreatedDate: 28/01/2022
	 * @Updated By :
	 * @Updated Date: 
	 * @param qaUrl,url,env
	 * @Comments
	 * 
	 */
	public String open(String testUrl, String qaUrl, String devUrl, String env) throws InterruptedException {
		if (env.equalsIgnoreCase("Test")) {
		pageManager.navigate(testUrl);
		pageManager.sleep(3000);
		//pageManager.waitForPageLoaded(); 
		log.info("Stage URL is selected");
		}
		else if (env.equalsIgnoreCase("QA")) {
		//pageManager.waitForPageLoaded();
		pageManager.navigate(qaUrl);
		pageManager.sleep(6000);
		//pageManager.waitForPageLoaded();
		log.info("Stage2 URL is selected");
		}
		else if (env.equalsIgnoreCase("Dev")) {
		//pageManager.waitForPageLoaded();
		pageManager.navigate(devUrl);
		pageManager.sleep(15000);
		//pageManager.waitForPageLoaded();
		log.info("Stage3 URL is selected");
		}
		String title = pageManager.getTitle();
		return title;
	}
	
	public String open(String url, String env) throws InterruptedException {
		
		loadURLs(url);
		ArrayList urls = boostURLs;
		
		if (env.equalsIgnoreCase("Test")) {
			System.out.println("Url is: " + urls.get(0).toString());
		pageManager.navigate(urls.get(0).toString());
		pageManager.sleep(3000);
		//pageManager.waitForPageLoaded(); 
		log.info("Stage URL is selected");
		}
		else if (env.equalsIgnoreCase("QA")) {
		//pageManager.waitForPageLoaded();
			System.out.println("Url is: " + urls.get(1).toString());
			pageManager.navigate(urls.get(1).toString());
		pageManager.sleep(6000);
		//pageManager.waitForPageLoaded();
		log.info("Stage2 URL is selected");
		}
		else if (env.equalsIgnoreCase("Dev")) {
		//pageManager.waitForPageLoaded();
			System.out.println("Url is: " + urls.get(2).toString());
			pageManager.navigate(urls.get(2).toString());
		pageManager.sleep(15000);
		//pageManager.waitForPageLoaded();
		log.info("Stage3 URL is selected");
		}
		String title = pageManager.getTitle();
		return title;
	}
	
	public void brokenLink(String testUrl, String qaUrl, String devURL, String env) throws ClientProtocolException, IOException {
		if (env.equalsIgnoreCase("Test")) {
			pageManager.findBrokenLinks(testUrl);
			log.info("Stage URL is selected");
			}
			else if (env.equalsIgnoreCase("QA")) {
			//pageManager.waitForPageLoaded();
			pageManager.findBrokenLinks(qaUrl);
			log.info("Stage2 URL is selected");
			}
			else if (env.equalsIgnoreCase("Dev")) {
			//pageManager.waitForPageLoaded();
			pageManager.findBrokenLinks(devURL);
			log.info("Stage2 URL is selected");
			}
		
	}
	
	public void brokenLink(String url) throws ClientProtocolException, IOException {
			pageManager.findBrokenLinks(url);
		
	}
	
	public void checkCurrentUrlStatus() throws ClientProtocolException, IOException 
	{
		pageManager.getUrlStatusCode(pageManager.getPageCurrentURL());
	}
	
	public String getPageUrl() throws InterruptedException {
		pageManager.sleep(3000);
		String currentUrl = pageManager.getPageCurrentURL();
		return currentUrl;
	}	
	
	public int checkGetResponse(String url) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.getResponseCode(url);
		return code;
	}
	
	public int checkUpdatePatchResponse(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.updatePatchResponseCode(url, jsonBody);
		return code;
	}
	
	public int checkPostResponse(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.postResponseCode(url, jsonBody);
		return code;
	}

	public String checkPostResponseBody(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		String code = post.postResponseBody(url, jsonBody);
		return code;
	}
	
	public int checkPutResponse(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.putResponseCode(url, jsonBody);
		return code;
	}	
	
	public int checkBriteVerifyResponse(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.emailVerificationCode(url, jsonBody);
		return code;
	}	
	
	public int checkSmartyAddress(String url) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		int code = post.smartyAddressCode(url);
		return code;
	}
	
	  public int checkDeleteResponse(String url, String jsonBody) throws
	  IOException { PostJsonObject post=new PostJsonObject(); int code =
	  post.deleteResponseCode(url, jsonBody); return code; }
	 
	
	public void validateStatus(int actual, int expected, String api) throws Exception
	{
		if (actual == expected) {
			String msg = "Pass: "+api+" API has response code "+actual;
			pageManager.testpass(msg);
		}
		else
		{
			String msg = "Fail: "+api+" API has failed with response code "+actual;
			pageManager.testfailAPI(msg);
		}
	}
	
	
	public void compCheckRegisteredUrl(String urls, String baseUrl, String title) throws Exception
	{
	String [] subUrls = urls.split(";");
	for(String str2 : subUrls){
	String newUrl = baseUrl+str2;
	pageManager.navigate(newUrl);
	pageManager.sleep(2000);
	if (title.equalsIgnoreCase(pageManager.getTitle()))
	{
	pageManager.testpass("Pass: Link "+newUrl+" is accessible to registered user only");
	}
	else {
	pageManager.testfail("Fail: Link "+newUrl+" is not redirected to Login screen");
	}

	}
	}
	
	public String checkEnv(String testUrl, String qaUrl, String devUrl, String env) throws ClientProtocolException, IOException {
		String baseUrl = null;
		if (env.equalsIgnoreCase("Test"))
		{
		baseUrl = testUrl;
		}
		else if (env.equalsIgnoreCase("QA"))
		{
		baseUrl = qaUrl;
		}
		else if (env.equalsIgnoreCase("Dev"))
		{
		baseUrl = devUrl;
		}
		return baseUrl;
		}

	public String checkGetResponseBody(String url) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		String body = post.getResponseBody(url);
		return body;
	}
	
	public String checkPostResponsebeacon(String url, String jsonBody) throws IOException
	{
		PostJsonObject post=new PostJsonObject();
		String code = post.postResponseBody(url, jsonBody);
		JSONObject jsonObj = new JSONObject(code);
		String token = jsonObj.getString("token");
		return token;
	}
	
	public JSONObject createTokenBeacon(String customerId)
	{
		JSONObject main = new JSONObject();
		main.put("agentId", "agentid");
		main.put("dishCustomerId", customerId);
		main.put("accountNumber", customerId);
		return main;
	}
}