package com.admin.budgetrook.pipeline.command;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class BinarizeCommand implements Command<MatPayload> {

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Mat gray = new Mat();
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(gray, gray, new Size(7, 7), 0);
		int blockSize = (src.width() + src.height()) / 200;
		if(blockSize < 2) {
			blockSize = 3;
		} else if (blockSize % 2 != 1){
			blockSize++;
		}
		Imgproc.adaptiveThreshold(gray, gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, 10);
		Imgproc.threshold(gray, gray, 0, 255, Imgproc.THRESH_BINARY_INV);
		input.setValue(gray);
		return input;
	}

}
