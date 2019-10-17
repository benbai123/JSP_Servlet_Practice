package com.blogspot.benbai123.selenium.gettingstarted;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Simply getting started for JAVA Selenium,
 * I do not have firefox installed so use ChromeDriver
 * 
 * Download ChromeDriver and specify path manually,
 * this way do not need WebDriverManager, only need selenium-java
 * 
 * Download ChromeDriver here: 
 * https://chromedriver.chromium.org/downloads
 * 
 * Check what version of Chrome do you have 
 * https://www.whatismybrowser.com/detect/what-version-of-chrome-do-i-have
 * 
 * @author benbai123
 *
 */
public class GettingStartedWithDownloadedChromeDriver {
	public static void main(String[] args) throws InterruptedException {
		/* Specify path of chromedriver.exe
		 * 
		 * Assume I download and extract chromedriver.exe to
		 * D:\Downloads\webdriver
		 */
		System.setProperty("webdriver.chrome.driver",
				String.join(File.separator, "D:", "Downloads", "webdriver", "chromedriver.exe")
			);
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
