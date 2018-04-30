package com.admin.budgetrook.ocr;

import java.io.File;

import com.admin.budgetrook.managers.OpenCVManager;
import com.admin.budgetrook.managers.TesseractManager;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Hello world!
 *
 */
public class App {
	private static final File SOURCE_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\1-no-barcode.png");
	private static final File DESTINATION_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\result.png");
	private static final File TEST_DESTINATION_FILE = new File("D:\\Grafiki\\OCRdata\\remix\\fixed\\result2.png");
	public static void main(String[] args) {

		System.out.println("Starting");
		try {
			OpenCVManager ocv = new OpenCVManager();
//			ocv.deskew(DESTINATION_FILE, TEST_DESTINATION_FILE);
			System.out.println(makeOcr(DESTINATION_FILE));
		} catch (TesseractException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finished");

	}

	private static String makeOcr(File file) throws Exception {
		TesseractManager tess = initTesseract();
		String result;
		if (file.isDirectory()) {
			long time = System.currentTimeMillis();
			result = tess.doRecrusiveOcr(file);
			result += String.format("Total elapsed time: %f seconds", (System.currentTimeMillis() - time) / 1000.0f);
		} else {
			result = tess.doOcr(file);
		}
		return result;
	}

	private static TesseractManager initTesseract() throws Exception {
		Tesseract t = new Tesseract();
		t.setDatapath("lib/tessdata");
		t.setLanguage("pol");
		TesseractManager tess = new TesseractManager(t, new File("report.txt"));
		return tess;
	}

}
