package testcases.demoblaze;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.MalformedURLException;

import  variables.demoblaze.UserVariables;

public class WebdriverSettings {
	
	 String remote_node_url;
	 WebDriver driver;

	public WebDriver driverSettings(String strbrowser) throws MalformedURLException{

		if(UserVariables.RUN_MODE.equalsIgnoreCase("Local"))
		{
			if(strbrowser.equalsIgnoreCase("chrome"))
			{
				//For local system
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();	
			
			}
			else if(strbrowser.equalsIgnoreCase("firefox"))
			{
				//For local system
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				driver=new FirefoxDriver();	
			}
			else
			{
				System.out.println("Error - Invalid browser !!!");
			}
		}
		else if(UserVariables.RUN_MODE.equalsIgnoreCase("Remote"))
		{
			remote_node_url = "http://"+UserVariables.GIRD_USER+":"+UserVariables.GIRD_PASSWORD+"@"+UserVariables.GIRD_HOST+"/wd/hub";
			String time_stamp = new SimpleDateFormat("MM_dd_yyyy_HHmmss").format(new Date());
			DesiredCapabilities cap = new DesiredCapabilities();
			if(strbrowser.equalsIgnoreCase("chrome"))
			{				
				//For remote system
				cap.setCapability(CapabilityType.BROWSER_NAME,BrowserType.CHROME);
				cap.setCapability("name", "DemoBlaze-Test-Chrome-"+time_stamp);
			}
			else if(strbrowser.equalsIgnoreCase("firefox"))
			{
				//For remote system
				cap.setCapability(CapabilityType.BROWSER_NAME,BrowserType.FIREFOX);
				cap.setCapability("name", "DemoBlaze-Test-Firefox-"+time_stamp);
			}
			else
			{
				System.out.println("Error - Invalid browser !!!");
			}
			//For remote system execution by Zalenium
			cap.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);		
			cap.setCapability("build", "DemoBlaze-Build-"+time_stamp);
			cap.setCapability("testFileNameTemplate", "{testName}_{browser}_{timestamp}");
			cap.setCapability("idleTimeout", 120);
			cap.setCapability("recordVideo", true);
			driver = new RemoteWebDriver(new URL(remote_node_url),cap);
		}
		else
		{
			System.out.println("ERROR - Run mode is invalid !!");
		}
		
		return driver;
	}

}
