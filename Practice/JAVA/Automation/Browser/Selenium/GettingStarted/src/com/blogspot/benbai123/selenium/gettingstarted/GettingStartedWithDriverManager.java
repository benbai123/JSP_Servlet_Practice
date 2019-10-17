package com.blogspot.benbai123.selenium.gettingstarted;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Simply getting started for JAVA Selenium,
 * I do not have firefox installed so use ChromeDriver
 * 
 * use Driver Manager to set up ChromeDriver,
 * need the dependency below in pom.xml
 * 
 *		<dependency>
 *			<groupId>io.github.bonigarcia</groupId>
 *			<artifactId>webdrivermanager</artifactId>
 *			<version>3.7.1</version>
 *		</dependency>
 * 
 * @author benbai123
 *
 */
public class GettingStartedWithDriverManager {
	public static void main(String[] args) throws InterruptedException {
		// set up ChromeDriver
		WebDriverManager wdm = ChromeDriverManager.getInstance(ChromeDriver.class);
		/* specify the version
		 * in fact it will still download correct version even
		 * remove this line. Awesome!
		 */
		wdm.version("77.0.3865.40");
		wdm.setup();
		// show where the chromedriver.exe is
		System.out.println(wdm.getBinaryPath());
		
		// create Selenium ChromeDriver
		WebDriver driver = new ChromeDriver();
		String url = "https://google.com";
		// open url
		driver.get(url);
		// get query input field
		WebElement element = driver.findElement(By.name("q"));
		// send what you want to query
		element.sendKeys("Cheese!");
		// submit
		element.submit();
		// delay for human eyes
		Thread.sleep(2000);
		/* close browser
		 * NOTE: should use quit() instead of close()
		 *   or chromedriverg.exe probably will not be released
		 */
		driver.quit();
	}
}
