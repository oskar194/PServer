package com.admin.budgetrook.ocr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

import com.admin.budgetrook.managers.PipelineManager;

public class App {
	private static final PipelineManager PIPELINE = new PipelineManager();
	private static final Logger log = Logger.getLogger(App.class);
	
	private static final File SRC = new File("D:\\Grafiki\\OCRdata\\remix\\perfect\\22.jpg");
	private static final File DST = new File("D:\\Grafiki\\OCRdata\\results\\fixed\\deskew.jpg");
	
	public static void main(String[] args) {
		logToFile(processRecursive(new File("D:\\Grafiki\\OCRdata\\remix")));
//		processRecursive(new File("D:\\Grafiki\\OCRdata\\remix"));
//		System.out.println("Starting");
//		System.out.println(PIPELINE.process(SRC, DST));
//		System.out.println("Finished");
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
			if(writer != null) {
				writer.close();
			}
		}
	}
	
	private static String processRecursive(File folder) {
		System.out.println("Start!");
		StringBuilder sb = new StringBuilder();
		for (File child : folder.listFiles()) {
			for(File f : child.listFiles()) {
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
		String result = PIPELINE.process(f, new File("D:\\Grafiki\\OCRdata\\results\\recursive\\" +  f.getParentFile().getName() + "-" +  f.getName()));
		long after = System.currentTimeMillis();
		return String.format("File %s\n %s\n Elapsed time: %f sec", f.getParentFile().getName() + "/" +  f.getName(), result, (after - before)/1000.0f);
	}
}
