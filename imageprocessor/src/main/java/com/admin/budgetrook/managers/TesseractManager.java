package com.admin.budgetrook.managers;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractManager {
	private Tesseract tesseract;

	public TesseractManager() throws Exception {
		tesseract = new Tesseract();
		tesseract.setDatapath("src/main/resources/tessdata");
		tesseract.setLanguage("pol");
	}

	public String doOcr(File file) {
		try {
			return tesseract.doOCR(file);
		} catch (TesseractException e) {
			e.printStackTrace();
			return null;
		}
	}
}
