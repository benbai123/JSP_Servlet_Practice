package com.blogspot.benbai123.selenium.visualtesting;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Test Visual Testing with Selenium
 * 
 * @author benbai123
 *
 */
public class VisualTessting {
	// Assume this is a test case
	// for a ticket on issue tracker
	private static String _ticket = "123456";
	public static void main (String[] args) throws Exception {
		WebDriver driver = null;
		try {
			ChromeDriverManager.getInstance(ChromeDriver.class).setup();
			driver = new ChromeDriver();
			
			// init Visual Testing
			VisualTestingImpl visualTestingImpl = VisualTestingImpl
					.getInstance(driver, _ticket);
			
			driver.get("https://google.com");
			driver.findElement(By.name("q")).sendKeys("1");
			/* take screenshot and do visual testing
			 * will use inner counter of VisualTestingImpl
			 * for file name
			 */
			visualTestingImpl.test();
			
			driver.findElement(By.name("q")).sendKeys("3");
			/* do test with specified file name
			 */
			visualTestingImpl.test("the_second_shot");
			
			driver.findElement(By.name("q")).sendKeys("3");
			visualTestingImpl.test();
	
			for (File f : visualTestingImpl.getAddedShots()) {
				System.out.println("added: "+f.getAbsolutePath());
			}
			for (File f : visualTestingImpl.getChangedShots()) {
				System.out.println("changed: "+f.getAbsolutePath());
			}
		} finally {
			driver.quit();
		}
	}
}
