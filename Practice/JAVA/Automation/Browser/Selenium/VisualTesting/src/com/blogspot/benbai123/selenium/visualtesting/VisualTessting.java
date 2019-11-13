package com.blogspot.benbai123.selenium.visualtesting;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	// for a ticket "123456" on issue tracker
	private static String _ticket = "123456";
	public static void main (String[] args) throws Exception {
		WebDriver driver = null;
		try {
			// init WebDriver
			ChromeDriverManager.getInstance(ChromeDriver.class).setup();
			driver = new ChromeDriver();
			
			// init Visual Testing
			VisualTestingImpl visualTestingImpl = VisualTestingImpl
					.getInstance(driver, _ticket);
			
			// go to google.com, type something
			driver.get("https://google.com");
			driver.findElement(By.name("q")).sendKeys("1");
			/* take screenshot and do visual testing
			 * will use inner counter of VisualTestingImpl
			 * for file name if not specified
			 */
			visualTestingImpl.test();
			
			driver.findElement(By.name("q")).sendKeys("2");
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
			/* assume accept the_second_shot as expected
			 * probably via a web based Dashboard
			 */
			visualTestingImpl.accept("the_second_shot");
			
			// test the_second_shot again
			System.out.println(" ---- test second time ---- ");
			visualTestingImpl.getAddedShots().clear();
			visualTestingImpl.getChangedShots().clear();
			
			driver.findElement(By.name("q")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "12");
			// test
			visualTestingImpl.test("the_second_shot");
			if (visualTestingImpl.getAddedShots().size() > 0
					|| visualTestingImpl.getChangedShots().size() > 0 ) {
				for (File f : visualTestingImpl.getAddedShots()) {
					System.out.println("added: "+f.getAbsolutePath());
				}
				for (File f : visualTestingImpl.getChangedShots()) {
					System.out.println("changed: "+f.getAbsolutePath());
				}
			} else {
				// make sure you see this line
				System.out.println(" ---- nothing changed/added ---- ");
			}
		} finally {
			driver.quit();
		}
	}
}
