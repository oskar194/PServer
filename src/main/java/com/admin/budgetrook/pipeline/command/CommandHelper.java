package com.admin.budgetrook.pipeline.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

class CommandHelper {
	private static final Logger log = Logger.getLogger(CommandHelper.class);
	
	static RotatedRect boundText(Mat src) {
		Mat points = Mat.zeros(src.size(), src.type());
		Core.findNonZero(src, points);
		MatOfPoint mop = new MatOfPoint(points);
		MatOfPoint2f points2f = new MatOfPoint2f(mop.toArray());
		RotatedRect rect = Imgproc.minAreaRect(points2f);
		return rect;
	}

	static void drawRectangleWithLines(Mat imageDest, RotatedRect rotatedRect, Scalar color, int thickness) {
		Point[] rectPoints = new Point[4];
		rotatedRect.points(rectPoints);
		int size = rectPoints.length;
		for (int i = 0; i < size; i++)
			Imgproc.line(imageDest, rectPoints[i], rectPoints[(i + 1) % size], color, thickness);
	}

	static void drawRectangleWithLines(Mat imageDest, Rect rect, Scalar color, int thickness) {
		Point[] rectPoints = computePoints(rect);
		int size = rectPoints.length;
		for (int i = 0; i < size; i++)
			Imgproc.line(imageDest, rectPoints[i], rectPoints[(i + 1) % size], color, thickness);
	}

	private static Point[] computePoints(Rect rect) {
		Point[] rectPoints = new Point[4];
		rectPoints[0] = new Point(rect.x, rect.y);
		rectPoints[1] = new Point(rect.x + rect.width, rect.y);
		rectPoints[2] = new Point(rect.x + rect.width, rect.y + rect.height);
		rectPoints[3] = new Point(rect.x, rect.y + rect.height);
		return rectPoints;
	}

	static Mat computeNewMat(Mat src) {
		double len = Math.max(src.size().width, src.size().height);
		Mat result = new Mat(new Size(len, len), src.type());
		return result;
	}

	static List<Rect> detectLettersBounding(Mat img) {
		int maxWidth = 750;
		int maxHeight = 15;
		double min = 100;
		List<Rect> boundingRect = new ArrayList<Rect>();
		Mat imageSobel = new Mat();
		Mat imageTreshold = new Mat();
		Mat element = new Mat();

		Imgproc.Sobel(img, imageSobel, CvType.CV_8U, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
		element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(maxWidth, maxHeight));
		Imgproc.threshold(imageSobel, imageTreshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		Imgproc.morphologyEx(imageSobel, imageSobel, Imgproc.MORPH_CLOSE, element);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(imageTreshold, contours, new Mat(), 0, 1);
		for (MatOfPoint contour : contours) {
			if (contour.size().height >= min) {
				log.info("Height: " + contour.size().height);
				log.info("Width: " + contour.size().width);
				MatOfPoint2f contour_poly = new MatOfPoint2f();
				Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), contour_poly, 3, true);
				Rect appRect = Imgproc.boundingRect(new MatOfPoint(contour_poly.toArray()));
				boundingRect.add(appRect);
			}
		}
		return boundingRect;
	}
}
