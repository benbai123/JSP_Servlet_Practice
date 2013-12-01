package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** Extract resource/test.xlsx to folder tmp,
 * then Zip it to dist/test.xlsx
 * (since xlsx is a zip file)
 * 
 * References:
 * 		http://stackoverflow.com/questions/9324933/what-is-a-good-java-library-to-zip-unzip-files
 * 		http://stackoverflow.com/questions/12562519/zip4j-zipping-for-files-and-folders-in-java
 * 		examples downloaded from http://www.lingala.net/zip4j/download.php
 *
 */
public class Test {
	public static void main (String args[]) throws ZipException, FileNotFoundException {
		String source = "resource/test.xlsx";
		String dest = "tmp/";
		// create zip file object with source
		ZipFile zipFile = new ZipFile(source);
		// extract it to dest folder (tmp)
		zipFile.extractAll(dest);
		// zip again (zip tmp/* to dist/test.xlsx
		zipFilesAndFolders(dest);
	}
	public static void zipFilesAndFolders (String sourceFolder) throws FileNotFoundException, ZipException {
		File base = new File(sourceFolder);
		ArrayList<File> filesToZip = new ArrayList<File>();
		// get all files/folders under sourceFolder
		if (base.isDirectory()) {
			filesToZip.addAll(Arrays.asList(base.listFiles()));
		}
		// init zip file
		ZipFile zipFile = new ZipFile("dist/test.xlsx");

		// init zip parameters
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setEncryptFiles(false);

		// add files/folders into zipFile
		for (File f : filesToZip) {
			if (f.isDirectory()) {
				zipFile.addFolder(f.getAbsolutePath(), parameters);
			} else {
				zipFile.addFile(f, parameters);
			}
		}
	}
}
