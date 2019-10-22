package com.blogspot.benbai123.selenium.commandstesting;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Test Selenium Commands
 * 
 * @author benbai123
 *
 */
public class CommandsTesting {
	/** Selenium Web Driver */
	private static WebDriver _driver;
	/** JavaScript Executor (Convert from _driver) */
	private static JavascriptExecutor _js;
	
	private static String _testingPage = "http://benbai123.github.io/TestPages/Automation/Selenium/CommandsTesting.html";
	private static long _delayForHumanEyes = 500L;
	public static void main (String[] args) {
		init();
		try {
			goToPage();
			
			testElementsSelection();
			testInput();
			testSelect();
		} finally {
			done();
		}
	}

	private static void init() {
		// set up ChromeDriver
		WebDriverManager wdm = ChromeDriverManager.getInstance(ChromeDriver.class);
		/* specify the version
		1 * in fact it will still download correct version even
		 * remove this line. Awesome!
		 */
		wdm.version("77.0.3865.40");
		wdm.setup();
		
		// create Selenium ChromeDriver
		_driver = new ChromeDriver();
		_js = ((JavascriptExecutor)_driver);
	}

	private static void goToPage() {
		_driver.get(_testingPage);
	}

	/**
	 *  Test select element(s)
	 */
	private static void testElementsSelection() {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("elements-selection"));
		// variable for find element
		WebElement found = null;
		/* test find element by id
		 * value of inpOne should be changed to "found"
		 * then "found 2" "found 3" "found 4"
		 */
		// by id
		found = testingBlock.findElement(By.id("inpOne"));
		// mark testing areas for eyes
		markTestingAreas(testingBlock, found);
		found.sendKeys("found");
		waitForEyes();
		// by css selector of id
		found = testingBlock.findElement(By.cssSelector("#inpOne"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//*[@id=\"inpOne\"]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
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
		found = testingBlock.findElement(By.className("inpTwo"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, found);
		found.sendKeys("found");
		waitForEyes();
		// by css selector of class name
		found = testingBlock.findElement(By.cssSelector(".inpTwo"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//*[contains(@class, 'inpTwo')]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
		waitForEyes();
		// by absolute xpath
		found = _driver.findElement(By.xpath("/html/body/div/*[contains(@class, 'inpTwo')]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 4");
		waitForEyes();
		
		/* test find element by name
		 * value of inpThree should be changed to "found"
		 * then "found 2" "found 3" "found 4"
		 */
		// by name
		found = testingBlock.findElement(By.name("inpThree"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, found);
		found.sendKeys("found");
		waitForEyes();
		// by css selector of name
		found = testingBlock.findElement(By.cssSelector("[name=\"inpThree\"]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//*[@name=\"inpThree\"]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
		waitForEyes();
		// by absolute xpath
		found = _driver.findElement(By.xpath("/html/body/div/*[@name=\"inpThree\"]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 4");
		waitForEyes();
		
		/* test find element by tagName
		 * value of textarea should be changed to "found"
		 * then "found 2" "found 3" "found 4"
		 */
		// by tagName
		found = testingBlock.findElement(By.tagName("textarea"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, found);
		found.sendKeys("found");
		waitForEyes();
		// by css selector of tagName
		found = testingBlock.findElement(By.cssSelector("textarea"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 2");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//textarea[1]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 3");
		waitForEyes();
		// by absolute xpath
		found = _driver.findElement(By.xpath("/html/body/div/textarea[1]"));
		found.sendKeys(Keys.chord(Keys.CONTROL, "a"), "found 4");
		waitForEyes();

		/* test find element by linkText
		 * value of linkOne should be changed to "google found"
		 * then "google found 3" "google found 4" (no CSS selector)
		 */
		// by linkText
		found = testingBlock.findElement(By.linkText("google"));
		switchTestingAreas(testingBlock, found);
		changeTextContent(found, "google found");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//a[text()='google found']"));
		changeTextContent(found, "google found 3");
		waitForEyes();
		// by absolute xpath
		found = _driver.findElement(By.xpath("/html/body/div/a[text()='google found 3']"));
		changeTextContent(found, "google found 4");
		waitForEyes();
		
		/* test find element by partialLinkText
		 * value of linkTwo should be changed to "github found"
		 * then "github found 3" "github found 4" (no CSS selector)
		 */
		// by partialLinkText
		found = testingBlock.findElement(By.partialLinkText("ithu"));
		switchTestingAreas(testingBlock, found);
		changeTextContent(found, "github found");
		waitForEyes();
		// by relative xpath
		found = testingBlock.findElement(By.xpath("//a[contains(text(), 'ub found')]"));
		changeTextContent(found, "github found 3");
		waitForEyes();
		// by absolute xpath
		found = _driver.findElement(By.xpath("/html/body/div/a[contains(text(), 'ithub fo')]"));
		changeTextContent(found, "github found 4");
		waitForEyes();
		
		/*
		 * Test select multiple
		 * the 3 input after inp4Multiple should be
		 * cnanged to "multiple found" then "multiple found 2"
		 */
		// test select multiple elements
		found = testingBlock.findElement(By.className("testMultiple"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, found);
		List<WebElement> eles = testingBlock.findElements(By.className("inp4Multiple"));
		for (WebElement ele : eles) {
			ele.sendKeys("multiple found");
		}
		waitForEyes();
		
		// test select multiple elements by javascript
		eles = (List<WebElement>)_js.executeScript("return document.querySelectorAll('.inp4Multiple')");
		for (WebElement ele : eles) {
			ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), "multiple found 2");
		}
		waitForEyes();
	}

	/**
	 * Try interact with input elements
	 */
	private static void testInput() {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("input-elements"));
		// text input
		WebElement input = testingBlock.findElement(By.className("text-input"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, input);
		// input some text
		input.sendKeys("some text");
		waitForEyes();
		// select all then delete
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		input.sendKeys(Keys.DELETE);
		waitForEyes();
		
		// readonly
		input = testingBlock.findElement(By.className("readonly-text-input"));
		// mark testing areas for eyes
		switchTestingAreas(testingBlock, input);
		// input some text, no effect
		input.sendKeys("some text 2");
		waitForEyes();
		
		try {
			// disabled
			input = testingBlock.findElement(By.className("disabled-text-input"));
			// mark testing areas for eyes
			switchTestingAreas(testingBlock, input);
			// input some text, will throw Exception
			input.sendKeys("some text 3");
			waitForEyes();
		} catch (Exception e) {
			System.out.println("Exception when send keys to disabled input\n\t"
					+e.getMessage());
		}
		
		// TODO checkbox radio number range color date
	}

	private static void testSelect() {
		// TODO Auto-generated method stub
		
	}

	private static void done() {
		_driver.quit();
	}

	private static void waitForEyes() {
		try {
			Thread.sleep(_delayForHumanEyes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void changeTextContent (WebElement ele, String text) {
		_js.executeScript("arguments[0].textContent = '"+text+"';", ele);
	}

	private static void markTestingAreas (WebElement... eles) {
		for (WebElement e : eles) {
			_js.executeScript("markTestingArea(arguments[0])", e);
		}
	}
	
	private static void switchTestingAreas (WebElement... eles) {
		unmarkAllTestingAreas();
		markTestingAreas(eles);
	}

	private static void unmarkAllTestingAreas () {
		_js.executeScript("unmarkAllTestingAreas();");
	}
}
