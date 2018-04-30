package com.admin.budgetrook.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractManager {
	private Tesseract tesseract;
	private File outputFile;

	public TesseractManager(Tesseract t, File logOutput) throws Exception {
		loadTesseractLib();
		this.tesseract = t;
		this.outputFile = logOutput;
	}

	public void logToFile(String input) {
		if (outputFile.exists()) {
			writeStringToFile(input, outputFile);
		} else {
			try {
				outputFile.createNewFile();
				writeStringToFile(input, outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void writeStringToFile(String input, File dest) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest)));
			writer.write(input);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String doRecrusiveOcr(File dir) {
		StringBuilder sb = new StringBuilder();
		for (File file : dir.listFiles()) {
			sb.append(doOcr(file));
		}
		return sb.toString();
	}

	public String doOcr(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(String.format("Started processing photo named: %s\n", file.getName()));
			long time = System.currentTimeMillis();
			sb.append(tesseract.doOCR(file));
			sb.append(String.format("Time elapsed: %f seconds\n", (System.currentTimeMillis() - time) / 1000.0f));
			sb.append("-----------------------------------------\n\n");
			return sb.toString();
		} catch (TesseractException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadTesseractLib() throws Exception {
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

	public Tesseract getTesseract() {
		return tesseract;
	}

	public void setTesseract(Tesseract tesseract) {
		this.tesseract = tesseract;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
}
