package com.admin.inz.server.budgetrook.image.processing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class ImageProcessor {

	private static final String FILE_PATH = "D:\\ApplicationUpload\\result.jpg";

//	public static void saveImage(JSONObject jsonObj) {
//		byte[] imageByteArray;
//		try {
//			imageByteArray = decodeImage(jsonObj.get("imageData").toString());
//			FileOutputStream imageOutFile = new FileOutputStream(FILE_PATH);
//			imageOutFile.write(imageByteArray);
//			imageOutFile.close();
//		} catch (JSONException e1) {
//			e1.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
	
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
