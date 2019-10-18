package com.blogspot.benbai123.selenium.commandstesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CommandsTesting {
	private static WebDriver _driver;
	private static String _testingPage = "file:///D:/things/benbai123/git/benbai123.github.io/TestPages/Automation/Selenium/CommandsTesting.html";
	public static void main (String[] args) {
		createWebDriver();
		goToPage();
		
		testElementsSelection();
		testInput();
		testRadio();
		testCheckbox();
		testSelect();
		
		done();
	}

	private static void createWebDriver() {
		// set up ChromeDriver
		WebDriverManager wdm = ChromeDriverManager.getInstance(ChromeDriver.class);
		/* specify the version
		 * in fact it will still download correct version even
		 * remove this line. Awesome!
		 */
		wdm.version("77.0.3865.40");
		wdm.setup();
		
		// create Selenium ChromeDriver
		_driver = new ChromeDriver();
	}

	private static void goToPage() {
		_driver.get(_testingPage);
	}

	private static void testElementsSelection() {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("elements-selection"));
		/* test find element by id
		 * value of inpOne should be changed to "found"
		 * then "found 2" "found 3" "found 4"
		 */
		// by id
		testingBlock.findElement(By.id("inpOne")).sendKeys("found");
		waitForEyes();
		// by css selector of id
		testingBlock.findElement(By.cssSelector("#inpOne"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		testingBlock.findElement(By.xpath("//*[@id=\"inpOne\"]"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
		waitForEyes();
		// by absolute xpath
		_driver.findElement(By.xpath("/html/body/div/*[@id=\"inpOne\"]"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 4");
		waitForEyes();
		/* test find element by class name
		 * value of inpTwo should be changed to "found"
		 * then "found 2" "found 3" "found 4"
		 */
		// by class name
		testingBlock.findElement(By.className("inpTwo")).sendKeys("found");
		waitForEyes();
		// by css selector of class name
		testingBlock.findElement(By.cssSelector(".inpTwo"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		testingBlock.findElement(By.xpath("//*[@class=\"inpTwo\"]"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
		waitForEyes();
		// by absolute xpath
		_driver.findElement(By.xpath("/html/body/div/*[@class=\"inpTwo\"]"))
			.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 4");
		waitForEyes();
		// TODO name, tag, linkText
	}

	private static void testInput() {
		// TODO Auto-generated method stub
		
	}

	private static void testRadio() {
		// TODO Auto-generated method stub
		
	}

	private static void testCheckbox() {
		// TODO Auto-generated method stub
		
	}

	private static void testSelect() {
		// TODO Auto-generated method stub
		
	}

	private static void done() {
		_driver.quit();
	}

	private static void waitForEyes() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
