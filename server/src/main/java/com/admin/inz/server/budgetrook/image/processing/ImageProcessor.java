package com.admin.inz.server.budgetrook.image.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.admin.budgetrook.managers.PipelineManager;
import com.admin.inz.server.budgetrook.dto.ParseDto;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.ImageRepository;
import com.admin.inz.server.budgetrook.services.ImageService;
import com.admin.inz.server.budgetrook.services.UserService;

@Component
public class ImageProcessor {

	@Autowired
	ImageService imageService;
	@Autowired
	UserService userService;
	@Autowired
	ImageRepository imageRepo;

	private static final PipelineManager PIPELINE = new PipelineManager();

	private static final String FILE_PATH = "D:\\ApplicationUpload\\";
	private static final String SUFFIX = "-processed";
	private static final String FILE_TYPE = ".jpg";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

	/*
	 * private static final File SRC = new
	 * File("D:\\Grafiki\\OCRdata\\remix\\perfect\\22.jpg"); private static final
	 * File DST = new File("D:\\Grafiki\\OCRdata\\results\\fixed\\deskew.jpg");
	 */

	public ParseDto saveAndProcess(byte[] bytes, Long imageId) throws Exception {
		String[] paths = makeName();
		saveImage(bytes);
		ParseDto result = process(paths[0], paths[1]);
		saveToDatabase(bytes, imageId);
		return result;
	}

	public ParseDto process(String source, String destination) {
		String processorResult = PIPELINE.process(new File(source), new File(destination));
		ParseDto dto = new ParseDto();
		dto.setTotal(findTotal(processorResult));
		dto.setDate(findDate(processorResult));
		return dto;
	}

	private String findTotal(String processorResult) {
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

	public String findDate(String processorResult) {
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

	public void saveImage(byte[] bytes) {
		byte[] imageByteArray;
		try {
			imageByteArray = bytes;
			String[] paths = makeName();
			FileOutputStream imageOutFile = new FileOutputStream(paths[0]);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// private void saveToDatabase(byte[] bytes) {
	// Image image = new Image();
	// image.setBytesBase64(Base64.getEncoder().encodeToString(bytes));
	// image.setName(makeName()[0]);
	// image.setOwner(userService.getUserByEmail("admin@admin.com"));
	// imageService.saveImage(image);
	// }

	private void saveToDatabase(byte[] bytes, Long imageId) {
		System.out.println("saveToDatabase imageId: " + imageId);
		Image image = imageRepo.findById(imageId);
		System.out.println("saveToDatabase image: " + image.toString());
		image.setBytesBase64(Base64.getEncoder().encodeToString(bytes));
		imageRepo.save(image);
	}

	private String[] makeName() {
		String pathBase = FILE_PATH + FORMAT.format(new Date());
		return new String[] { pathBase + FILE_TYPE, pathBase + SUFFIX + FILE_TYPE };
	}

	// public void saveImage(String data) {
	// byte[] imageByteArray;
	// try {
	// imageByteArray = decodeImage(data);
	// String[] paths = makeName();
	// FileOutputStream imageOutFile = new FileOutputStream(paths[0]);
	// imageOutFile.write(imageByteArray);
	// imageOutFile.close();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// private byte[] decodeImage(String encodedData) {
	// return Base64.getDecoder().decode(encodedData);
	// }

}
