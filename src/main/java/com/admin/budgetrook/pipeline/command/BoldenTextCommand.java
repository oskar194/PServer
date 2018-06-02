package com.admin.budgetrook.pipeline.command;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class BoldenTextCommand implements Command<MatPayload> {

	@Override
	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
		Imgproc.dilate(src, src, kernel);
		input.setValue(src);
		return input;
	}

}
