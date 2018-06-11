package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class DeskewCommand implements Command<MatPayload> {
	private static final Logger log = Logger.getLogger(DeskewCommand.class);

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(src.width() / 5, 1));
		Mat dilated = new Mat(new Size(src.width() * 2 , src.height() * 2), src.type());
		src.copyTo(new Mat(dilated, new Rect(src.width() /2 , src.height() /2 ,src.width(), src.height())));
		Imgproc.dilate(dilated, dilated, kernel);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		double meanAngle = 0;
//		Imgproc.cvtColor(dilated, dilated, Imgproc.COLOR_GRAY2BGR);
		for (MatOfPoint contour : contours) {
			RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
//			CommandHelper.drawRectangleWithLines(dilated, rect, new Scalar(255,255,0), 3);
			meanAngle += normalizeAngle(rect.angle);
		}
		double angle = meanAngle/contours.size();
		log.info("Angle is: " + angle);
//		rotate(src, angle, CommandHelper.computeNewMat(src));
		rotate(src, angle, src);
		input.setValue(src);
		return input;
	}

	private double normalizeAngle(double angle) {
		if ((Math.abs(angle)) == Double.MIN_VALUE || angle == 0d) {
			return 0d;
		}
		if(angle < -45) {
			return -(90+angle);
		}
		return angle;
	}

	private void rotate(Mat src, double angle, Mat dst) {
		Mat rotation = Imgproc.getRotationMatrix2D(new Point(src.size().height / 2.0d, src.size().width / 2.0d), angle,
				1d);
		Imgproc.warpAffine(src, src, rotation, dst.size());
	}

}
