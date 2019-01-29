package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class RemoveBarcodeCommand implements Command<MatPayload> {

	@Override
	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Mat kernel = new Mat();
		kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(50, 2));
		Mat dilated = new Mat();
		Imgproc.dilate(src, dilated, kernel);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
		MatOfPoint maxH = CommandHelper.getMaxContourHeight(contours);

		List<MatOfPoint> toDelete = new ArrayList<MatOfPoint>();

		for (MatOfPoint c : contours) {
			Rect r = Imgproc.boundingRect(c);
			if (r.height >= Imgproc.boundingRect(maxH).height * 0.98) {
				toDelete.add(c);
			}
		}
		if (toDelete.size() < 7 && toDelete.size() > 3) {
			Imgproc.fillPoly(src, toDelete, new Scalar(0));
		}
		return input;
	}

}
