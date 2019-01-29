package com.admin.budgetrook.pipeline.command;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class RotateCommand implements Command<MatPayload>{

	@Override
	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Mat rotated = new Mat();
		Core.rotate(src, rotated, Core.ROTATE_90_CLOCKWISE);
		
		input.setValue(rotated);
		return input;
	}

}
