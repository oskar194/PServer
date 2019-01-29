package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class DetectTextBlocksCommand implements Command<MatPayload> {

	// https://stackoverflow.com/questions/23506105/extracting-text-opencv
	@Override
	public MatPayload execute(MatPayload input) {
		Mat img = input.getValue();
		double imgHeight = img.size().height;
		double imgWidth = img.size().width;
		Mat dilated = new Mat();
//		WORKING Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 1));
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(50, 1));
		Imgproc.dilate(img, dilated, kernel);
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat accumulated = new Mat(img.size(), img.type());
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
		for (MatOfPoint contour : contours) {
			Rect boundingRect = Imgproc.boundingRect(contour);
			double rectHeight = boundingRect.size().height;
			double rectWidth = boundingRect.size().width;
			if (rectHeight > img.height() / 13 || rectHeight < imgHeight / 115) {
				continue;
			}
			if (boundingRect.y > imgHeight - (imgHeight / 35 * 4)) {
				continue;
			}
			if (rectWidth > imgWidth * 0.5) {
				continue;
			}
			new Mat(img, boundingRect).copyTo(new Mat(accumulated, boundingRect));
			
			input.setValue(accumulated);
		}
		return input;
	}

}
