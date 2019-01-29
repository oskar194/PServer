package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class ExtractBiggestRectangleCommand implements Command<MatPayload> {

	@Override
	public MatPayload execute(MatPayload input) {
		Mat img = input.getValue();
		Mat blurred = new Mat();
		Imgproc.medianBlur(img, blurred, 9);
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.medianBlur(blurred, blurred, 9);
		
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(img.width(), 180));
//		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(img.width(), 100));
		Mat dilated = new Mat();
		Imgproc.dilate(blurred, dilated, kernel);
		
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		Rect rect = Imgproc.boundingRect(CommandHelper.getMaxContourArea(contours));
		Mat roi = new Mat(img, rect);
		input.setValue(roi);
		return input;
	}
}
