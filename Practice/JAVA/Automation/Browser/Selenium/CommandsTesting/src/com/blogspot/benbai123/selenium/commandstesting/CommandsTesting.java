package com.blogspot.benbai123.selenium.commandstesting;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Test Selenium Commands
 * 
 * testing page and JavaScript file
 *   http://benbai123.github.io/TestPages/Automation/Selenium/CommandsTesting.html
 *   http://benbai123.github.io/TestPages/Automation/Selenium/js/commands_testing.js
 * 
 * refs:
 * 	https://www.javatpoint.com/selenium-webdriver-commands
 *  https://stackoverflow.com/questions/12729265/switch-tabs-using-selenium-webdriver-with-java
 *  https://stackoverflow.com/questions/11736027/webdriver-wait-for-element-using-java
 *  https://stackoverflow.com/questions/20903231/how-to-wait-until-an-element-is-present-in-selenium
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
			goToTestPage();
			
			testElementsSelection();
			testInput();
			testSelect();
			testIFrames();
			testNewBrowserWindows();
			testBrowserHistory();
			testFlowControl();
			testScreenShot();
		} catch (Exception e) {
			e.printStackTrace();
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

	private static void goToTestPage() {
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
		 * Test select multiple elements
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

	/**
	 * test select/option
	 */
	private static void testSelect() {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("select-option"));
		// get single select element
		WebElement select = testingBlock.findElement(By.className("test-single-select"));
		switchTestingAreas(testingBlock, select);
		
		/*
		 * select another option by click, Selenium not work well this way,
		 * the select will not be closed and the
		 * selected option will not get focus
		 */
		select.click();
		select.findElement(By.cssSelector("option[value='sleep']")).click();
		// click again to close dropdown
		select.click();
		waitForEyes(2000);

		// select by key control, works better
		select.click();
		select.sendKeys(Keys.UP);
		waitForEyes();
		select.sendKeys(Keys.ENTER);
		waitForEyes(2000);
		
		// deselect by JS
		_js.executeScript("arguments[0].selectedIndex = -1;", select);
		waitForEyes(2000);
		
		// get test-multiple-select
		select = testingBlock.findElement(By.className("test-multiple-select"));
		switchTestingAreas(testingBlock, select);
		// select cheese
		select.findElement(By.cssSelector("option[value='cheese']")).click();
		waitForEyes();
		// control-select ham
		Actions act = new Actions(_driver);
		act.keyDown(Keys.CONTROL).build().perform();
		select.findElement(By.cssSelector("option[value='ham']")).click();
		act.keyUp(Keys.CONTROL).build().perform();
		waitForEyes();

		/* shift-select egg and beef
		 * not works, see the issue below
		 *   https://github.com/mozilla/geckodriver/issues/645
		 */
		act = new Actions(_driver);
		act.keyDown(Keys.SHIFT).build().perform();
		select.findElement(By.cssSelector("option[value='beef']")).click();
		act.keyUp(Keys.SHIFT).build().perform();

		waitForEyes(2000);
		
		/*
		 * simulate shift (range) select
		 * but will not trigger event
		 */
		// clear all first
		_js.executeScript("arguments[0].querySelectorAll('option:checked').forEach(e => e.selected = false);", select);
		waitForEyes();
		WebElement ham = select.findElement(By.cssSelector("option[value='ham']"));
		WebElement beef = select.findElement(By.cssSelector("option[value='beef']"));
		// select ham
		ham.click();
		waitForEyes();
		// range select to beef
		rangeSelect(ham, beef);
		waitForEyes();
		
		// deselect tomato to cheese
		WebElement tomato = select.findElement(By.cssSelector("option[value='tomato']"));
		WebElement cheese = select.findElement(By.cssSelector("option[value='cheese']"));
		tomato.click();
		waitForEyes();
		rangeSelect(tomato, cheese);
		waitForEyes();
		
	}
	/**
	 * test move to iframe and back
	 * @throws Exception 
	 */
	private static void testIFrames() throws Exception {
		// move to iframe001
		_driver.switchTo().frame("iframe001");
		String content = _driver.findElement(By.className("iframe-content")).getText();
		String expected = "content in iframe 001";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);

		// move to iframe002 inside iframe001
		_driver.switchTo().frame("iframe002");
		content = _driver.findElement(By.className("iframe-content")).getText();
		expected = "content in iframe 002";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);

		// back to default window
		_driver.switchTo().defaultContent();
		// move to iframe003
		_driver.switchTo().frame("iframe003");
		content = _driver.findElement(By.className("iframe-content")).getText();
		expected = "content in iframe 003";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);
		
		// back to default window
		_driver.switchTo().defaultContent();
	}

	/**
	 * test move to child window(s) and back	 * 
	 * longer method name since I cannot call it "testWindows"...
	 * 
	 * ref: https://stackoverflow.com/questions/12729265/switch-tabs-using-selenium-webdriver-with-java
	 * @throws Exception 
	 */
	private static void testNewBrowserWindows() throws Exception {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("child-windows"));
		WebElement testElem = testingBlock.findElement(By.tagName("a"));
		switchTestingAreas(testingBlock, testElem);
		
		/*
		 * keep tracking each window handle,
		 * WebDriver.getWindowHandles returns a Set of handles
		 * the order of handles probably not guaranteed
		 */
		Map<String, String> winHandles = new HashMap<String, String>();
		// track original window
		addWindowHandle("origin", winHandles);
		
		// open window_001
		testElem.click();
		// track the opened window
		addWindowHandle("win001", winHandles);
		// switch to win001
		_driver.switchTo().window(winHandles.get("win001"));
		waitForEyes();
		String content = _driver.findElement(By.className("window-content")).getText();
		String expected = "content in window 001";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);
		
		// back to original window
		_driver.switchTo().window(winHandles.get("origin"));
		// test window_002
		testElem = testingBlock.findElement(By.className("newwin-002"));
		switchTestingAreas(testingBlock, testElem);
		testElem.click();
		addWindowHandle("win002", winHandles);
		_driver.switchTo().window(winHandles.get("win002"));
		waitForEyes();
		content = _driver.findElement(By.className("window-content")).getText();
		expected = "content in window 002";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);

		_driver.switchTo().window(winHandles.get("origin"));
		// test window_003
		testElem = testingBlock.findElement(By.className("newwin-003"));
		switchTestingAreas(testingBlock, testElem);
		testElem.click();
		addWindowHandle("win003", winHandles);
		_driver.switchTo().window(winHandles.get("win003"));
		waitForEyes();
		content = _driver.findElement(By.className("window-content")).getText();
		expected = "content in window 003";
		System.out.println(content);
		assertTrue(expected.equals(content), "content should be "+expected);
		
		// close win001~win003
		_driver.switchTo().window(winHandles.get("win001")).close();
		waitForEyes();
		_driver.switchTo().window(winHandles.get("win002")).close();
		waitForEyes();
		_driver.switchTo().window(winHandles.get("win003")).close();
		waitForEyes();
		// back to origin
		_driver.switchTo().window(winHandles.get("origin"));
	}

	private static void testBrowserHistory() throws Exception {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("browser-history"));
		switchTestingAreas(testingBlock);
		// to next page 001
		testingBlock.findElement(By.className("nextpage001")).click();
		waitForEyes(1500);
		// to next page 002
		_driver.findElement(By.className("nextpage002")).click();
		waitForEyes(1500);
		// back
		_driver.navigate().back();
		waitForEyes(1500);
		System.out.println(_driver.findElement(By.className("next-page-content")).getText());
		// forward
		_driver.navigate().forward();
		waitForEyes(1500);
		System.out.println(_driver.findElement(By.className("next-page-content")).getText());
		// back to first page (second history) from last page
		_js.executeScript("window.history.go(-(window.history.length - 2))");
		// relocate testing block
		testingBlock = _driver.findElement(By.className("browser-history"));
		switchTestingAreas(testingBlock);
		waitForEyes(1500);
		
		// test history.pushState
		WebElement ele = _driver.findElement(By.className("push-state-link"));
		ele.click();
		waitForEyes(1500);
		assertTrue(!ele.isDisplayed(), "push-state-link is visible");
		_driver.navigate().back();
		waitForEyes(1500);
		assertTrue(ele.isDisplayed(), "push-state-link is not visible");
		_driver.navigate().forward();
		waitForEyes(1500);
		assertTrue(!ele.isDisplayed(), "push-state-link is visible");
		// TODO Auto-generated method stub
		
	}

	private static void testFlowControl() {
		// locate to testing block first
		WebElement testingBlock = _driver.findElement(By.className("flow-control"));
		WebElement ele = testingBlock.findElement(By.className("add-child"));
		switchTestingAreas(testingBlock, ele);
		waitForEyes();
		
		ele.click();
		/* wait at most 20 seconds
		 * will stop waiting as soon as the added-child is presented
		 */
		WebDriverWait wait = new WebDriverWait(_driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("added-child")));
		
		ele = testingBlock.findElement(By.className("to-be-hidden"));
		switchTestingAreas(testingBlock, ele);
		ele.click();
		
		// wait until to-be-hidden is hidden
		// also try java8 lambda
		new FluentWait<WebElement>(ele) // create FluentWait instance, pass ele into it
			.withTimeout(Duration.ofSeconds(20)) // wait at most 20 seconds
			.pollingEvery(Duration.ofMillis(500)) // check every 500 millis
			.ignoring(Exception.class) // ignore any Exception
			.until((WebElement e) -> {
				// check whether the passed in WebElement is hidden
				return !e.isDisplayed();
			});
		waitForEyes(2000);
	}

	private static void testScreenShot() throws Exception {
		_driver.get("https://www.google.com");
		WebElement q = _driver.findElement(By.name("q"));

		q.sendKeys("aa");
		q.sendKeys(Keys.TAB);
		File ss = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		Files.move(ss.toPath(), Paths.get("aa.png"), StandardCopyOption.REPLACE_EXISTING);
		
		q.sendKeys(Keys.chord(Keys.CONTROL, "a"), "bb");
		q.sendKeys(Keys.TAB);
		ss = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		Files.move(ss.toPath(), Paths.get("bb.png"), StandardCopyOption.REPLACE_EXISTING);
	}

	private static void assertTrue(boolean isTrue, String errMsg) throws Exception{
		if (!isTrue) throw new Exception(errMsg);
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
			posY = Math.max(posY, e.getLocation().getY());
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

	/**
	 * Simulate range-select of multiple select
	 * 
	 * start and end should be 2 different elements
	 * 
	 * @param start
	 * @param end
	 */
	private static void rangeSelect(WebElement start, WebElement end) {
		WebElement selectElement = (WebElement)_js.executeScript("return arguments[0].parentNode;", start);
		boolean isSelected = start.isSelected();
		List<WebElement> options = new Select(selectElement).getOptions();
		int sidx = -1, eidx = -1;
		for (int i = 0; i < options.size(); i++) {
			String value = options.get(i).getAttribute("value");
			if (value.equals(start.getAttribute("value"))
					|| value.equals(end.getAttribute("value"))) {
				if (sidx == -1)
					sidx = i;
				else {
					eidx = i;
					break;
				}
			}
		}
		String script = "arguments[0].querySelectorAll('option')"
				+ ".forEach(" // for each option
				+ 	"(e, i) => {" // e: element, i: index
				+ 	" if (i >= "+sidx+" && i <= "+eidx+")" // in the range
				+		" e.selected = "+isSelected+"});"; // update selected status
		_js.executeScript(script, selectElement);
	}
	/**
	 * get the window handle that not being tracked,
	 * then put it into tracking map with key
	 * 
	 * @param key
	 * @param winHandles
	 */
	private static void addWindowHandle(String key, Map<String, String> winHandles) {
		for (String handle : _driver.getWindowHandles()) {
			if (!winHandles.containsValue(handle)) {
				winHandles.put(key, handle);
				break;
			}
		}
	}
}
