package com.admin.budgetrook.managers;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractManager {
	private Tesseract tesseract;
	private static final Logger log = Logger.getLogger(TesseractManager.class);

	public TesseractManager() throws Exception {
		loadTesseractLib();
		tesseract = new Tesseract();
		tesseract.setDatapath("lib/tessdata");
		tesseract.setLanguage("pol");
	}

	public String doOcr(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			log.info(String.format("Started processing photo named: %s\n", file.getName()));
			long time = System.currentTimeMillis();
			sb.append(tesseract.doOCR(file));
			log.info(String.format("Time elapsed: %f seconds\n", (System.currentTimeMillis() - time) / 1000.0f));
			log.info("-----------------------------------------");
			return sb.toString();
		} catch (TesseractException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void loadTesseractLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String ver;
		String libraryPath = "lib/tess4j/win32-x86/";
		ver = "32";
		if (model.equals("64")) {
			libraryPath = "lib/tess4j/win32-x86-64/";
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
}
