package com.blogspot.benbai123.dynamiccompileclass.gettingstarted;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestDynamicCompile {
	// assume you have D:\ ... :\
	private static String _sourceFolder = "D:" + File.separator + "javaDynamicSource";

	public static void main(String[] args) throws Exception {
		String packageName = "dynamic.compiled";
		String className = "DynamicClass";
		String source = getSource(packageName, className);
		System.out.println("### source:\n" + source);
		File sourceFile = saveSource(packageName, className, source);
		compileSource(sourceFile);
		instantiateAndCallMethod(packageName, className, "test");
	}

	private static String getSource(String packageName, String className) {
		StringBuilder sb = new StringBuilder("");
		sb.append("package ").append(packageName).append(";").append("\n")
				.append("import com.blogspot.benbai123.dynamiccompileclass.gettingstarted.Parent;").append("\n")
				.append("public class ").append(className).append(" extends Parent {").append("\n").append("\t")
				.append("@Override").append("\n").append("\t").append("public void test () {").append("\n")
				.append("\t\t").append("System.out.println(\"test in ").append(className).append(" is called\");")
				.append("\n").append("\t\t").append("super.test();").append("\n").append("\t").append("}\n")
				.append("}\n");
		return sb.toString();
	}

	private static File saveSource(String packageName, String className, String source) throws Exception {
		File root = new File(_sourceFolder);
		File sourceFile = new File(root,
				packageName.replaceAll("\\.", "/" + File.separator + "/") + File.separator + className + ".java");
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
		return sourceFile;
	}

	private static void compileSource(File sourceFile) {
		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		System.out.println(compiler);
		compiler.run(null, null, null, sourceFile.getPath());
	}

	private static void instantiateAndCallMethod(String packageName, String className, String method) throws Exception {
		System.out.println("### call method of dynamic class ");
		// Load and instantiate compiled class.
		File sourceFolder = new File(_sourceFolder);
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { sourceFolder.toURI().toURL() });
		Class<?> cls = Class.forName(packageName + "." + className, true, classLoader);
		Object instance = cls.newInstance();
//    	System.out.println(instance); // Should print "dynamic.compiled.DynamicClass@hashcode".

		// call method
		Method m = cls.getDeclaredMethod(method);
		m.invoke(instance);
	}
}
