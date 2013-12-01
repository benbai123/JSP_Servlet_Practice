package test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
	public static void main (String args[]) throws FileNotFoundException, IOException {
		// create ByteArrayOutputStream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// write some content into it
		out.write("test".getBytes());
		// store it to a file
		out.writeTo(new FileOutputStream ("dist/tmp.txt"));
	}
}
