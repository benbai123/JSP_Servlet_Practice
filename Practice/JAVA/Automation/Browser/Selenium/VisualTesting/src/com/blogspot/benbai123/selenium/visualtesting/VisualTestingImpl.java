package com.blogspot.benbai123.selenium.visualtesting;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.blogspot.benbai123.image.diff.ImageDiffImpl;

public class VisualTestingImpl {
	/** root folder for store all visual testing images */
	private static File _rootFolder = new File("selenium/visualTesting");
	static {
		// create root folder if not exists
		if (!_rootFolder.exists()) {
			_rootFolder.mkdirs();
		}
	}
	/** WebDriver used to take screenshots */
	private WebDriver _driver;
	/** specified ticket, will be used to construct folders to store images */
	private String _ticket;
	
	/** Folders for visual testing of this instance, constructed with _ticket */
	private TestingFolders _testingFolders;
	/** counter used to generate file names for screenshots if not specified */
	private AtomicInteger _cnt = new AtomicInteger(1);
	/** number of locks */
	private int _lockNum = 5;
	/** locks used to prevent different thread do test on same file name */
	private ReentrantLock[] _locks = new ReentrantLock[_lockNum];
	
	/** added screenshots (cannot find expected image with the file name of actual image) */
	private List<File> _addedShots = new ArrayList<File>();
	/** changed screenshots (both actual and expected image are exist but different) */
	private List<File> _changedShots = new ArrayList<File>();
	
	/** factory method */
	public static VisualTestingImpl getInstance (WebDriver driver, String ticket) {
		VisualTestingImpl impl = new VisualTestingImpl();
		impl._driver = driver;
		impl._ticket = ticket;
		impl._testingFolders = impl.new TestingFolders();
		
		IntStream.range(0, impl._lockNum).parallel().forEach((i) -> {
			impl._locks[i] = new ReentrantLock();
		});
		return impl;
	}
	public List<File> getAddedShots () {
		return _addedShots;
	}
	public List<File> getChangedShots () {
		return _changedShots;
	}
	/**
	 * Do Visual Testing without file name specified
	 * 
	 * @throws Exception
	 */
	public void test () throws Exception {
		// generate file name with inner counter
		int no = _cnt.getAndIncrement();
		String fileName = _ticket+"_"+String.format("%03d", no);
		// test with generated file name
		test(fileName);
	}
	public void test(String fileName) throws Exception {
		// get lock with fileName
		ReentrantLock lock = _locks[(fileName.hashCode()& 0xfffffff) % _lockNum];
		lock.lock();
		try {
			// take a screenshot and put it to "actual" folder
			File actual = takesScreenShot(fileName);
			// try to get the expected file
			File expected = getFile(_testingFolders._expectedFolder, fileName);
			// always diff even expected doesn't exist
			ImageDiffImpl diffImpl = diff(expected, actual);
			ImageIO.write(diffImpl.result(), "png", // store diff result
					getFile(_testingFolders._diffFolder, fileName));
			if (!expected.exists()) {
				_addedShots.add(actual);
			} else if (diffImpl.different()) {
				_changedShots.add(actual);
			}
		} finally {
			lock.unlock();
		}
	}
	/**
	 * diff actual file with expected file
	 * 
	 * will auto generate a blank 1x1 BufferedImage for expected file
	 * if it does not exist
	 * 
	 * @param expected
	 * @param actual
	 * @return
	 * @throws Exception
	 */
	private ImageDiffImpl diff(File expected, File actual) throws Exception {
		ImageDiffImpl diffImpl = new ImageDiffImpl();
		diffImpl.config().ignoreAlpha(true);
		diffImpl.expected(expected).actual(actual).diff();
		return diffImpl;
	}
	/**
	 * take a screenshot and move it to actual folder
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private File takesScreenShot (String fileName) throws Exception {
		File tmp = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		File shot = getFile(_testingFolders._actualFolder, fileName);
		Files.move(tmp.toPath(), shot.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return shot;
	}
	/**
	 * make an actual image become expected image
	 * 
	 * @param fileName
	 */
	public void accept (String fileName) throws Exception {
		File actual = getFile(_testingFolders._actualFolder, fileName);
		File expected = getFile(_testingFolders._expectedFolder, fileName);
		Files.move(actual.toPath(), expected.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	private File getFile (File folder, String fileName) {
		return Paths.get(folder.getAbsolutePath(), fileName+".png").toFile();
	}
	/**
	 * inner class for holding folder information
	 * 
	 * @author benbai123
	 *
	 */
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
