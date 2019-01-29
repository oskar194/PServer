package com.admin.budgetrook.ocr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.opencv.core.Core;

import com.admin.budgetrook.managers.PipelineManager;

public class App {
	private static final PipelineManager PIPELINE = new PipelineManager();
	private static final Logger log = Logger.getLogger(App.class);

	private static final File SRC = new File("D:\\Grafiki\\OCRdata\\remix\\perfect\\22.jpg");
	private static final File DST = new File("D:\\Grafiki\\OCRdata\\results\\fixed\\deskew.jpg");

	private static final File PROD_SRC = new File("D:\\ApplicationUpload\\test\\28-01-2019-23-53-12.jpg");
	private static final File PROD_DST = new File("D:\\ApplicationUpload\\test\\deskew.jpg");

	public static void main(String[] args) {
		// logToFile(processRecursive(new File("D:\\Grafiki\\OCRdata\\remix")));
		// processRecursive(new File("D:\\Grafiki\\OCRdata\\remix"));
		// System.out.println("Starting");
		String processorResult = PIPELINE.process(PROD_SRC, PROD_DST);
		System.out.println(processorResult);
		System.err.println("TOTAL: " + findTotal(processorResult));
		System.err.println("DATE: " + findDate(processorResult));
		// System.out.println("Finished");
	}

	private static String findTotal(String processorResult) {
		String result = "";
		try {
			String stripped = processorResult.trim();
			Pattern pattern = Pattern.compile("(SUMA.*)");
			Matcher matcher = pattern.matcher(stripped);
			Pattern digits = Pattern.compile("[^0-9,\\.]");
			String res = "";
			while (matcher.find()) {
				res = stripped.substring(matcher.start(), matcher.end());
			}
			if (!"".equalsIgnoreCase(res)) {
				Matcher digitsMatcher = digits.matcher(res);
				if (digitsMatcher.find()) {
					result = digitsMatcher.replaceAll("").trim().replace(',', '.');
					Double d = Double.parseDouble(result);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			return "ERROR";
		}

		return result;
	}

	public static String findDate(String processorResult) {
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		String result = "";
		try {
			String stripped = processorResult.trim();
			Pattern datePattern = Pattern.compile("([0-9]{4}[—-][0-9]{2}[—-][0-9]{2})");
			Matcher matcher = datePattern.matcher(stripped);
			if (matcher.find()) {
				result = stripped.substring(matcher.start(), matcher.end()).replace('—', '-').trim();
				format.parse(result);
			}
		} catch (Exception e) {
			System.err.println(e);
			return "ERROR";
		}
		return result;
	}

	private static void logToFile(String result) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String name = dtf.format(now);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("D:\\Grafiki\\OCRdata\\results\\results\\" + name + ".txt", "UTF-8");
			writer.print(result);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private static String processRecursive(File folder) {
		System.out.println("Start!");
		StringBuilder sb = new StringBuilder();
		for (File child : folder.listFiles()) {
			for (File f : child.listFiles()) {
				sb.append("\n-------------------------------\n");
				sb.append(processFolder(f));
				sb.append("\n-------------------------------\n");
			}
		}
		System.out.println("Stop!");
		return sb.toString();
	}

	private static String processFolder(File f) {
		System.out.println(String.format("File: %s in %s", f.getName(), f.getParentFile().getName()));
		long before = System.currentTimeMillis();
		String result = PIPELINE.process(f, new File(
				"D:\\Grafiki\\OCRdata\\results\\recursive\\" + f.getParentFile().getName() + "-" + f.getName()));
		long after = System.currentTimeMillis();
		return String.format("File %s\n %s\n Elapsed time: %f sec", f.getParentFile().getName() + "/" + f.getName(),
				result, (after - before) / 1000.0f);
	}

	static {
		try {
			loadTesseractLib();
			loadOpenCVLib();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadTesseractLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String ver;
		String libraryPath = "src/main/resources/tess4j/win32-x86/";
		ver = "32";
		if (model.equals("64")) {
			libraryPath = "src/main/resources/tess4j/win32-x86-64/";
			ver = "64";
		}
		System.setProperty("java.library.path", libraryPath);
		Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
		sysPath.setAccessible(true);
		sysPath.set(null, null);
		System.loadLibrary("gsdll" + ver);
		System.loadLibrary("liblept1744");
		System.loadLibrary("libtesseract3051");
	}

	private static void loadOpenCVLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String libraryPath = "src/main/resources/opencv/x86/";
		if (model.equals("64")) {
			libraryPath = "src/main/resources/opencv/x64/";
		}
		System.setProperty("java.library.path", libraryPath);
		Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
		sysPath.setAccessible(true);
		sysPath.set(null, null);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
