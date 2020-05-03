package com.network_traffic.XHR_capture;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

public class XHRCapture {
	
	//read from local properties file
	String driverPath = "driverPath/location";
	String harFileName = "har_file_name";
	String user_data_directory = "user_data_directory_location";
	public WebDriver driver;
	public BrowserMobProxy proxy;
	
	@Before
	public void setUp() throws Exception {
		
	   // start the proxy
	    proxy = new BrowserMobProxyServer();
	    proxy.setTrustAllServers(true);
	    proxy.start(0);

	    //get the Selenium proxy object - org.openqa.selenium.Proxy;
	    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
	    
	    // configure it as a desired capability
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    
	    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
	    
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--ignore-certificate-errors","--user-data-dir=user_data_directory");
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    
		
	    //set chromedriver system property
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		driver = new ChromeDriver(capabilities);
		
	    // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // create a new HAR
	    proxy.newHar("preferred_file_name");

	    // open website
	    driver.get("website_link");
	    
	    //login
	    WebElement username = driver.findElement(By.id("username"));
	    WebElement password = driver.findElement(By.id("password"));
	    
        username.sendKeys("your_username");
        password.sendKeys("your_password");
        driver.findElement(By.id("loginbtn")).click();
        Thread.sleep(10000);
        driver.get("another_webpage_link");
        Thread.sleep(5000);
	}
	
	@Test
	public void testCaseOne() throws Exception {
		System.out.println("Navigating...");
		Thread.sleep(10000);
		System.out.println(driver.getTitle());
	}
	
	@After
	public void tearDown() {

		// get the HAR data
		Har har = proxy.getHar();

		// Write HAR Data in a File
		File harFile = new File(harFileName);
		try {
			har.writeTo(harFile);
		} catch (IOException ex) {
			 System.out.println (ex.toString());
		     System.out.println("Could not find file " + harFileName);
		}
		
		if (driver != null) {
			proxy.stop();
			driver.quit();
		}
		
		System.out.println("Done");
	}
}

