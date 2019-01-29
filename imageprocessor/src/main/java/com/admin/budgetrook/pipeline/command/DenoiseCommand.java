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

public class DenoiseCommand implements Command<MatPayload> {

	public MatPayload execute(MatPayload input) {
		Mat img = input.getValue();
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
//		WROKING Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(15, 15));
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(18,18));
		Mat dilated = new Mat();
		Imgproc.dilate(img, dilated, kernel);
		
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
		List<Rect> rrList = new ArrayList<Rect>(contours.size());
		double meanHeight = 0;
		double meanWidth = 0;
		double meanArea = 0;
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			rrList.add(rect);
			meanHeight += rect.height;
			meanWidth += rect.width;
			meanArea += rect.area();
		}
		meanHeight = meanHeight / contours.size();
		meanWidth = meanWidth / contours.size();
		meanArea = meanArea / contours.size();
		img = cropNoise(img, rrList, meanHeight, meanWidth, meanArea);
		input.setValue(img);
		return input;
	}

	private Mat cropNoise(Mat img, List<Rect> rrList, double meanHeight, double meanWidth, double meanArea) {
		Rect maxHeight = CommandHelper.getMaxHeightRect(rrList);
		if(maxHeight.height >= img.height() * 0.8 && maxHeight.width < meanWidth) {
			img = CommandHelper.cropRectangle(img, maxHeight);
		}
		Rect minArea = CommandHelper.getMinAreaRect(rrList);
		for (Rect r : rrList) {
			if(r.height > meanHeight *2 && r.width < meanWidth /2) {
				img = CommandHelper.cropRectangle(img, r);
			}
			if(r.width > meanWidth *2 && r.height < meanHeight /2) {
				img = CommandHelper.cropRectangle(img, r);
			}
			if(r.height < meanHeight /4 || r.width < meanWidth / 4) {
				img = CommandHelper.cropRectangle(img, r);
			}
			if(r.area() < minArea.area() * 4 ) {
				img = CommandHelper.cropRectangle(img, r);
			}
		}
		return img;
	}
}
