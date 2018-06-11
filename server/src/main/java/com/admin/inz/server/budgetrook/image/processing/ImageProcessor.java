package com.admin.inz.server.budgetrook.image.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.admin.budgetrook.managers.PipelineManager;

@Component
public class ImageProcessor {

	private static final String FILE_PATH = "D:\\ApplicationUpload\\result.jpg";
	private static final PipelineManager PIPELINE = new PipelineManager();

	private static final File SRC = new File("D:\\Grafiki\\OCRdata\\remix\\perfect\\22.jpg");
	private static final File DST = new File("D:\\Grafiki\\OCRdata\\results\\fixed\\deskew.jpg");

	public static String process() {
		return PIPELINE.process(SRC, DST);
	}

	public void saveImage(byte[] bytes) {
		byte[] imageByteArray;
		try {
			imageByteArray = bytes;
			FileOutputStream imageOutFile = new FileOutputStream(FILE_PATH);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveImage(String data) {
		byte[] imageByteArray;
		try {
			imageByteArray = decodeImage(data);
			FileOutputStream imageOutFile = new FileOutputStream(FILE_PATH);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private byte[] decodeImage(String encodedData) {
		return Base64.getDecoder().decode(encodedData);
	}
}
