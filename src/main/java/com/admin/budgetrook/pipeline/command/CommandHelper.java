package com.admin.budgetrook.pipeline.command;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.Core;
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
	private static class MatOfPointAreaComparator implements Comparator<MatOfPoint> {
		@Override
		public int compare(MatOfPoint left, MatOfPoint right) {
			if (area(left) > area(right)) {
				return 1;
			} else if (area(left) < area(right)) {
				return -1;
			}
			return 0;
		}

		private double area(MatOfPoint mop) {
			return Imgproc.contourArea(mop);
		}
	}

	private static class MatOfPointAreaWidth implements Comparator<MatOfPoint> {
		@Override
		public int compare(MatOfPoint left, MatOfPoint right) {
			if (width(left) > width(right)) {
				return 1;
			} else if (width(left) < width(right)) {
				return -1;
			}
			return 0;
		}

		private double width(MatOfPoint mop) {
			return Imgproc.boundingRect(mop).width;
		}
	}
	
	private static class MatOfPointAreaHeight implements Comparator<MatOfPoint> {
		@Override
		public int compare(MatOfPoint left, MatOfPoint right) {
			if (height(left) > height(right)) {
				return 1;
			} else if (height(left) < height(right)) {
				return -1;
			}
			return 0;
		}

		private double height(MatOfPoint mop) {
			return Imgproc.boundingRect(mop).height;
		}
	}

	private static class RectAreaComparator implements Comparator<Rect> {
		@Override
		public int compare(Rect left, Rect right) {
			if (area(left) > area(right)) {
				return 1;
			} else if (area(left) < area(right)) {
				return -1;
			}
			return 0;
		}

		private double area(Rect rr) {
			return rr.width * rr.height;
		}

	}

	private static class RectHeightComparator implements Comparator<Rect> {
		@Override
		public int compare(Rect left, Rect right) {
			if (height(left) > height(right)) {
				return 1;
			} else if (height(left) < height(right)) {
				return -1;
			}
			return 0;
		}

		private double height(Rect rr) {
			return rr.height;
		}
	}

	public static Rect getMaxHeightRect(Collection<Rect> rectList) {
		return Collections.max(rectList, new RectHeightComparator());
	}
	
	public static Rect getMinAreaRect(Collection<Rect> rectList) {
		return Collections.min(rectList, new RectAreaComparator());
	}
	
	public static MatOfPoint getMaxContourArea(Collection<MatOfPoint> contours) {
		if(contours.isEmpty()) {
			return null;
		}
		return Collections.max(contours, new MatOfPointAreaComparator());
	}

	public static MatOfPoint getMaxContourWidth(Collection<MatOfPoint> contours) {
		return Collections.max(contours, new MatOfPointAreaWidth());
	}
	
	public static MatOfPoint getMaxContourHeight(Collection<MatOfPoint> contours) {
		if(contours.isEmpty()) {
			return null;
		}
		return Collections.max(contours, new MatOfPointAreaHeight());
	}

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

	static Mat cropRectangle(Mat src, Rect rect) {
		Mat.zeros(rect.size(), src.type()).copyTo(new Mat(src, rect));
		return src;
	}

}
