package com.blogspot.benbai123.selenium.commandstesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Test Selenium Commands
 * 
 * testing page and JavaScript file
 *   http://benbai123.github.io/TestPages/Automation/Selenium/CommandsTesting.html
 *   http://benbai123.github.io/TestPages/Automation/Selenium/js/commands_testing.js
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
	@SuppressWarnings("unchecked")
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
		switchTestingAreas(testingBlock, input);
		
		// get initial value
		String text = input.getAttribute("value");
		System.out.println("initial value "+text);
		// select all then delete
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		input.sendKeys(Keys.DELETE);
		waitForEyes();
		// input some text
		input.sendKeys("some text");
		waitForEyes();
		// get updated value
		text = input.getAttribute("value");
		System.out.println("updated value "+text);
		
		// readonly
		input = testingBlock.findElement(By.className("readonly-text-input"));
		switchTestingAreas(testingBlock, input);
		// input some text, no effect
		input.sendKeys("some text 2");
		waitForEyes();
		
		try {
			// disabled
			input = testingBlock.findElement(By.className("disabled-text-input"));
			switchTestingAreas(testingBlock, input);
			// input some text, will throw Exception
			input.sendKeys("some text 3");
			waitForEyes();
		} catch (Exception e) {
			System.out.println("Exception when send keys to disabled input\n\t"
					+e.getMessage());
		}
		
		// checkbox
		input = testingBlock.findElement(By.className("test-checkbox"));
		WebElement label = (WebElement)_js.executeScript("return arguments[0].parentNode;", input);
		switchTestingAreas(testingBlock, label);
		text = label.getText();
		System.out.println("Label text of test-checkbox: "+text);
		// check it
		input.click();
		// get checked status
		System.out.println("test-checkbox checked: "+input.isSelected());
		// uncheck
		input.click();
		System.out.println("test-checkbox checked: "+input.isSelected());
		waitForEyes();
		
		// radio
		switchTestingAreas(testingBlock, testingBlock.findElement(By.className("test-radios")));
		// select female
		testingBlock.findElement(By.cssSelector("[type='radio'][name='gender'][value='female']")).click();
		waitForEyes();
		// select male
		testingBlock.findElement(By.cssSelector("[type='radio'][name='gender'][value='male']")).click();
		waitForEyes(2000);
		// get selected value
		input = findElementIfAny(testingBlock, By.cssSelector("[type='radio'][name='gender']:checked"));
		if (input != null) {
			System.out.println("Selected gender: "+input.getAttribute("value"));
			// deselect it
			_js.executeScript("arguments[0].checked = false;", input);
			waitForEyes(2000);
		}

		// number, 0~100 step 0.2
		input = testingBlock.findElement(By.className("test-number-input"));
		switchTestingAreas(testingBlock, input);
		// try to input some wrong value, should see some notification in browser
		input.sendKeys("0.3");
		_js.executeScript("arguments[0].blur();", input);
		waitForEyes(5000);
		// inc
		input.sendKeys(Keys.ARROW_UP);
		input.sendKeys(Keys.ARROW_UP);
		input.sendKeys(Keys.ARROW_UP);
		waitForEyes();
		
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"), "200");
		_js.executeScript("arguments[0].blur();", input);
		waitForEyes(5000);
		// dec
		input.sendKeys(Keys.ARROW_DOWN);
		input.sendKeys(Keys.ARROW_DOWN);
		input.sendKeys(Keys.ARROW_DOWN);
		waitForEyes();
		
		// range, 0~100 step 0.2
		input = testingBlock.findElement(By.className("test-range-input"));
		switchTestingAreas(testingBlock, input);
		// click at specific position
		clickAt(input, 30, 5);
		waitForEyes();

		// inc
		input.sendKeys(Keys.ARROW_UP);
		input.sendKeys(Keys.ARROW_UP);
		input.sendKeys(Keys.ARROW_UP);
		waitForEyes();

		// dec
		input.sendKeys(Keys.ARROW_DOWN);
		input.sendKeys(Keys.ARROW_DOWN);
		input.sendKeys(Keys.ARROW_DOWN);
		waitForEyes();
		
		// color input
		input = testingBlock.findElement(By.className("test-color-input"));
		switchTestingAreas(testingBlock, input);
		// change color
		_js.executeScript("arguments[0].value = '#8347AE'", input);
		waitForEyes();
		
		// date, 2019-01-06~2019-12-29, step 7 days, default 2019-10-27
		input = testingBlock.findElement(By.className("test-date-input"));
		switchTestingAreas(testingBlock, input);
		// get default date
		try {
			Date defaultDate = new SimpleDateFormat("yyyy-MM-dd").parse(
					(String)_js.executeScript("return arguments[0].value;", input) );
			System.out.println(defaultDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// change value to 2019-01-06, use JavaScript to set value directly
		// to avoid the gap between different browser
		_js.executeScript("arguments[0].value = '2019-01-05';", input);
		_js.executeScript("arguments[0].onchange();", input);
		waitForEyes(3000);
		// increase date by js (see commands_testing.js)
		_js.executeScript("addDays(arguments[0], 1);", input);
		waitForEyes(3000);
		// inc
		_js.executeScript("addDays(arguments[0], 1);", input);
		waitForEyes(3000);
		// dec 2 days
		_js.executeScript("addDays(arguments[0], -2);", input);
		waitForEyes(3000);
	}

	private static void testSelect() {
		// TODO Auto-generated method stub
		
	}

	private static void done() {
		_driver.quit();
	}

	private static void waitForEyes() {
		waitForEyes(_delayForHumanEyes);
	}
	private static void waitForEyes(long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void changeTextContent (WebElement ele, String text) {
		_js.executeScript("arguments[0].textContent = '"+text+"';", ele);
	}

	private static void markTestingAreas (WebElement... eles) {
		// mark by red border and scroll to most low/right element
		int posX = 0;
		int posY = 0;
		for (WebElement e : eles) {
			posX = Math.max(posX, e.getLocation().getX());
			posY = Math.max(posX, e.getLocation().getY());
			_js.executeScript("markTestingArea(arguments[0])", e);
		}
		_js.executeScript("window.scrollBy(" +posX +", " +(posY-100) +")");
	}
	
	private static void switchTestingAreas (WebElement... eles) {
		unmarkAllTestingAreas();
		markTestingAreas(eles);
	}

	private static void unmarkAllTestingAreas () {
		_js.executeScript("unmarkAllTestingAreas();");
	}

	private static WebElement findElementIfAny(WebElement from, By by) {
		List<WebElement> eles = from.findElements(by);
		if (!eles.isEmpty())
			return eles.get(0);
		return null;
	}
	
	private static void clickAt (WebElement ele, int posX, int posY) {
		Actions act = new Actions(_driver);
		act.moveToElement(ele).moveByOffset(posX, posY).click().perform();
	}

}
