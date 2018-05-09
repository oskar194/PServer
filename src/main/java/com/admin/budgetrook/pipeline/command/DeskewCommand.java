package com.admin.budgetrook.pipeline.command;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class DeskewCommand implements Command<MatPayload> {
	private static final Logger log = Logger.getLogger(DeskewCommand.class);

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		RotatedRect rect = CommandHelper.boundText(src);
		double angle = rect.angle;
		log.info("Angle is: " + angle);
//		CommandHelper.drawRectangleWithLines(src, rect, new Scalar(255, 255, 255), 5);
//		rotate(src, angle, CommandHelper.computeNewMat(src));
		rotate(src, angle, src);
		input.setValue(src);
		return input;
	}

	private void rotate(Mat src, double angle, Mat dst) {
		if ((Math.abs(angle)) == Double.MIN_VALUE || angle == 0d) {
			return;
		}
		if(angle < -89) {
			angle = -(angle + 90);
		}
		Mat rotation = Imgproc.getRotationMatrix2D(new Point(src.size().height / 2.0d, src.size().width / 2.0d), angle,
				1d);
		Imgproc.warpAffine(src, src, rotation, dst.size());
	}

}
