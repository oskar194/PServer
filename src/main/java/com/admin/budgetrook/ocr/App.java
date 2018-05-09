package com.admin.budgetrook.ocr;

import java.io.File;

import org.apache.log4j.Logger;

import com.admin.budgetrook.managers.PipelineManager;

/**
 * Hello world!
 *
 */
public class App {
	private static final PipelineManager PIPELINE = new PipelineManager();
	private static final Logger log = Logger.getLogger(App.class);
	
	private static final File SOURCE_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\1-no-barcode.png");
	private static final File SOURCE_FILE_BARCODE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\fixed1-barcode.png");
	private static final File SOURCE_FILE_DECORATIONS = new File(
			"D:\\Grafiki\\OCRdata\\remix\\fixed\\decorations-test.jpg");
	private static final File SOURCE_FILE_BIG_SUMA = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\big-suma.jpg");
	private static final File DESTINATION_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\result-pipe.png");
	private static final File TEST_DESTINATION_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\result2.png");

	public static void main(String[] args) {

		log.info("Starting");
		System.out.println(PIPELINE.process(SOURCE_FILE_DECORATIONS, DESTINATION_FILE));
		log.info("Finished");

	}
}
