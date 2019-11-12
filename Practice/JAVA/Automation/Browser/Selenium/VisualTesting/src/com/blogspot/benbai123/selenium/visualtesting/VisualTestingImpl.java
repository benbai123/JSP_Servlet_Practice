package com.blogspot.benbai123.selenium.visualtesting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.blogspot.benbai123.image.diff.ImageDiffImpl;

public class VisualTestingImpl {
	private static File _rootFolder = new File("selenium/visualTesting");
	static {
		if (!_rootFolder.exists()) {
			_rootFolder.mkdirs();
		}
	}
	
	private TestingFolders _testingFolders;
	private WebDriver _driver;
	private String _ticket;
	private AtomicInteger _cnt = new AtomicInteger(1);
	private List<File> _addedShots = new ArrayList<File>();
	private List<File> _changedShots = new ArrayList<File>();
	public static VisualTestingImpl getInstance (WebDriver driver, String ticket) {
		VisualTestingImpl impl = new VisualTestingImpl();
		impl._driver = driver;
		impl._ticket = ticket;
		impl._testingFolders = impl.new TestingFolders();
		return impl;
	}
	public void test () throws Exception {
		synchronized (this) {
			int no = _cnt.getAndIncrement();
			test(_ticket+"_"+String.format("%03d", no));
		}
	}
	public List<File> getAddedShots () {
		return _addedShots;
	}
	public List<File> getChangedShots () {
		return _changedShots;
	}
	public void test(String fileName) throws Exception {
		// take a screenshot and put it to "actual" folder
		File actual = takesScreenShot(fileName);
		File expected = getFile(_testingFolders._expectedFolder, fileName);
		// always diff even expected doesn't exist
		ImageDiffImpl diffImpl = diff(expected, actual);
		ImageIO.write(diffImpl.result(), "png", getFile(_testingFolders._diffFolder, fileName));
		if (!expected.exists()) {
			_addedShots.add(actual);
		} else if (diffImpl.different()) {
			_changedShots.add(actual);
		}
	}
	private ImageDiffImpl diff(File expected, File actual) throws Exception {
		BufferedImage expectedImage = expected.exists()?
			ImageIO.read(expected) :
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);;
		BufferedImage actualImage = ImageIO.read(actual);
		ImageDiffImpl diffImpl = new ImageDiffImpl();
		diffImpl.config().ignoreAlpha(true);
		diffImpl.expected(expectedImage).actual(actualImage).diff();
		return diffImpl;
	}
	private File takesScreenShot (String fileName) throws Exception {
		File tmp = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		File shot = getFile(_testingFolders._actualFolder, fileName);
		Files.move(tmp.toPath(), shot.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return shot;
	}
	private File getFile (File folder, String fileName) {
		return Paths.get(folder.getAbsolutePath(), fileName+".png").toFile();
	}
	private class TestingFolders {
		private File _expectedFolder;
		private File _actualFolder;
		private File _diffFolder;
		private TestingFolders () {
			_expectedFolder = initFolder("expected");
			_actualFolder = initFolder("actual");
			_diffFolder = initFolder("diff");
		}
		private File initFolder (String folderName) {
			File folder = Paths.get(_rootFolder.getAbsolutePath(), _ticket, folderName).toFile();
			folder.mkdirs();
			return folder;
		}
	}
}
