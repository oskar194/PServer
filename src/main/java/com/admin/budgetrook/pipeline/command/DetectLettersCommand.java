package com.admin.budgetrook.pipeline.command;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class DetectLettersCommand implements Command<MatPayload> {

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();

		List<Rect> boundingRect = CommandHelper.detectLettersBounding(src);
		for (Rect rect : boundingRect) {
			CommandHelper.drawRectangleWithLines(src, rect, new Scalar(255, 255, 255), 5);
		}
		input.setValue(src);
		return input;
	}



}
