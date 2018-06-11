package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class ExtractCashCommand implements Command<MatPayload> {

	@Override
	public MatPayload execute(MatPayload input) {
		Mat img = input.getValue();
		int x = 0;
		int y = (int) (img.height() * 0.46);
		int w = (int) (img.width());
		int h = (int) (img.height() * 0.39);
		img = new Mat(img, new Rect(x, y, w, h));
			img = extractFeatures(img);
		input.setValue(img);
		return input;
	}

	private Mat extractFeatures(Mat img) {
		Mat eroded = new Mat();
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 4));
		Imgproc.erode(img, eroded, kernel, new Point(0, 0), 4);
		kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(img.width()*2, img.height()* 0.15));
		Mat dilated = new Mat();
		Imgproc.dilate(eroded, dilated, kernel);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
		Rect boundingRect = Imgproc.boundingRect(CommandHelper.getMaxContourArea(contours));
		Mat accumulated = new Mat(img.size(), img.type());
		new Mat(img, boundingRect).copyTo(new Mat(accumulated, boundingRect));
		return accumulated;
	}
}
