package test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;

public class LESSUtils {
	public static void compile (File lessFile, File cssFile) throws LessException, IOException {
		if (lessFile == null 
				|| !lessFile.exists()) {
			// less file should exist
			throw new FileNotFoundException("LESS file not exists !");
		}
		// create less engine
		LessEngine engine = new LessEngine();
		if (!cssFile.getParentFile().exists()) {
			// create folders for css file
			boolean cssFileCreated = cssFile.getParentFile().mkdirs();
			if (!cssFileCreated) {
				throw new IOException("Folders for CSS file are not created !");
			}
		}
		// compile less file to css file
		engine.compile(lessFile, cssFile);
	}
}
