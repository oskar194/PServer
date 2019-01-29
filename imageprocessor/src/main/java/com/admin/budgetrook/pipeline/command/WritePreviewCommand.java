package com.admin.budgetrook.pipeline.command;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class WritePreviewCommand implements Command<MatPayload>{

	@Override
	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		Imgcodecs.imwrite("D:\\ApplicationUpload\\test\\previewCommand.jpg", src);
		return input;
	}

}
