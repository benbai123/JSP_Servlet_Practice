package com.blogspot.benbai123.selenium.visualtesting;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class VisualTestingImpl {
	private static File _defaultRootFolder = new File("selenium/visualTesting");
	static {
		prepareRootFolder(_defaultRootFolder);
	}
	private static void prepareRootFolder (File rootFolder) {
		if (!rootFolder.exists()) {
			rootFolder.mkdirs();
		}
	}
	
	private File _rootFolder;
	private WebDriver _driver;
	private String _ticket;
	private AtomicInteger _cnt = new AtomicInteger(1);
	public VisualTestingImpl () {
		_rootFolder = _defaultRootFolder;
	}
	public File rootFolder () {
		return _rootFolder;
	}
	public VisualTestingImpl rootFolder (File rootFolder) {
		_rootFolder = rootFolder;
		prepareRootFolder(_rootFolder);
		return this;
	}
	public VisualTestingImpl rootFolder (String rootFolderPath) {
		return rootFolder(new File(rootFolderPath));
	}
	public VisualTestingImpl webDriver (WebDriver driver) {
		_driver = driver;
		return this;
	}
	public VisualTestingImpl ticket (String ticket) {
		_ticket = ticket;
		return this;
	}
	public void testAndIncrement () throws Exception {
		synchronized (this) {
			int no = _cnt.getAndIncrement();
			test(no);
		}
	}
	private void test(int no) throws Exception {
		File actual = takesScreenShot(no);
		System.out.println(actual.getAbsolutePath());
	}
	private File takesScreenShot (int no) throws Exception {
		File ss = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		Path p = Files.move(ss.toPath(), getShotPath(no), StandardCopyOption.REPLACE_EXISTING);
		return p.toFile();
	}
	private Path getShotPath(int no) {
		Paths.get(_rootFolder.getAbsolutePath(), _ticket).toFile().mkdirs();
		return Paths.get(_rootFolder.getAbsolutePath(), _ticket, no+".png");
	}
}
