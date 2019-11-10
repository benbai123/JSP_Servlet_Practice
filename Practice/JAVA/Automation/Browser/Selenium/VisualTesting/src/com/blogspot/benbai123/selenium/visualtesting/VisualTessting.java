package com.blogspot.benbai123.selenium.visualtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class VisualTessting {
	public static void main (String[] args) throws Exception {
		ChromeDriverManager.getInstance(ChromeDriver.class).setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://google.com");
		driver.findElement(By.name("q")).sendKeys("1");
		VisualTestingImpl visualTesting = new VisualTestingImpl();
		visualTesting.ticket("123456").webDriver(driver);
		visualTesting.testAndIncrement();
		driver.findElement(By.name("q")).sendKeys("2");
		visualTesting.testAndIncrement();
		driver.findElement(By.name("q")).sendKeys("3");
		visualTesting.testAndIncrement();
		driver.quit();
	}
}
