package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class DetectTextBlocksCommand implements Command<MatPayload> {
	private static final Logger log = Logger.getLogger(DetectTextBlocksCommand.class);

	@Override
	public MatPayload execute(MatPayload input) {
		Mat img = input.getValue();
		double imgHeight = img.size().height;
		double imgWidth = img.size().width;
		log.info("imgHeight : " + imgHeight);
//https://stackoverflow.com/questions/23506105/extracting-text-opencv
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(85, 1));
		Mat dilated = new Mat();
		Imgproc.dilate(img, dilated, kernel);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat accumulated = new Mat(img.size(), img.type());
		Mat hierarchy = new Mat();
		Imgproc.findContours(dilated, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
		log.info("Contour size: " + contours.size());
		for (MatOfPoint contour : contours) {
			Rect boundingRect = Imgproc.boundingRect(contour);
			double rectHeight = boundingRect.size().height;
			double rectWidth = boundingRect.size().width;
			if (rectHeight > 150 || rectHeight < 40) {
				continue;
			}
			if(boundingRect.y > imgHeight - (imgHeight / 35 * 3)) {
				continue;
			}
			log.info("Rect x: " + boundingRect.x);
			Mat roi = new Mat(img, boundingRect);
			Mat accRoi = new Mat(accumulated, boundingRect);
			roi.copyTo(accRoi);
//			CommandHelper.drawRectangleWithLines(img, boundingRect, new Scalar(255, 255, 0), 5);
		}
		input.setValue(accumulated);
//		input.setValue(img);
		return input;
	}

}
