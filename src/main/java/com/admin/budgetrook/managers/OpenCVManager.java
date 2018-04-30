package com.admin.budgetrook.managers;

import java.io.File;
import java.lang.reflect.Field;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVManager {
	public OpenCVManager() throws Exception {
		loadOpenCVLib();
	}

	private void loadOpenCVLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String libraryPath = "lib/opencv/x86/";
		if (model.equals("64")) {
			libraryPath = "lib/opencv/x64/";
		}
		System.setProperty("java.library.path", libraryPath);
		Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
		sysPath.setAccessible(true);
		sysPath.set(null, null);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public void deskew(File source, File destination) {
		Mat src = loadImage(source);
//		binarize(src);
		RotatedRect rect = boundText(src);
		double angle = rect.angle;
		System.out.println("Angle is: " + angle);
		drawRectangleWithLines(src, rect, new Scalar(255,255,255), 5);
		rotate(src, angle, computeNewMat(src));
		saveFile(src, destination.getAbsolutePath());
	}

	private Mat computeNewMat(Mat src) {
		double len = Math.max(src.size().width, src.size().height);
		Mat result = new Mat(new Size(len,len), src.type());
		return result;
	}

	private RotatedRect boundText(Mat src) {
		Mat points = Mat.zeros(src.size(), src.type());
		Core.findNonZero(src, points);
		MatOfPoint mop = new MatOfPoint(points);
		MatOfPoint2f points2f = new MatOfPoint2f(mop.toArray());
		RotatedRect rect = Imgproc.minAreaRect(points2f);
		return rect;
	}

	private void binarize(Mat src) {
		try {
			Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
		} catch (Exception e) {
			System.out.println("Error m =" + e.getMessage());
			System.out.println("Skipping...");
		}
		Imgproc.threshold(src, src, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		Core.bitwise_not(src, src);
	}

	private Mat loadImage(File f) {
		Mat src = Imgcodecs.imread(f.getPath(), 0);
		return src;
	}

	private void saveFile(Mat src, String path) {
		Imgcodecs.imwrite(path, src);
	}

	private void rotate(Mat src, double angle, Mat dst) {
		if((Math.abs(angle)) == Double.MIN_VALUE || angle == 0d ) {
			return;
		}
		Mat rotation = Imgproc.getRotationMatrix2D(new Point(src.size().height / 2.0d, src.size().width / 2.0d), angle,
				1d);
		Imgproc.warpAffine(src, src, rotation, dst.size());
	}

	private void drawRectangleWithLines(Mat imageDest, RotatedRect rotatedRect, Scalar color, int thickness) {
		Point[] rectPoints = new Point[4];
		rotatedRect.points(rectPoints);
		int size = rectPoints.length;
		for (int i = 0; i < size; i++)
			Imgproc.line(imageDest, rectPoints[i], rectPoints[(i + 1) % size], color, thickness);
	}
}
