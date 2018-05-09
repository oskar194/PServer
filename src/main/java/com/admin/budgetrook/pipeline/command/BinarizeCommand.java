package com.admin.budgetrook.pipeline.command;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;
import com.lowagie.text.html.simpleparser.Img;

public class BinarizeCommand implements Command<MatPayload> {
	private static final Logger log = Logger.getLogger(BinarizeCommand.class);

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		try {
			Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
		} catch (Exception e) {
			log.error("Error m =" + e.getMessage());
			log.info("Skipping...");
		}
		// Imgproc.threshold(src, src, 200, 255, Imgproc.THRESH_BINARY |
		// Imgproc.THRESH_OTSU);
//		Imgproc.adaptiveThreshold(src, src, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 10, 10);
		Imgproc.threshold(src,src,95,255,Imgproc.THRESH_BINARY_INV);
//		Core.bitwise_not(src, src);
		input.setValue(src);
		return input;
	}

}
