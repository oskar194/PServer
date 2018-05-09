package com.admin.budgetrook.pipeline.input;

import org.opencv.core.Mat;

public class MatPayload extends Payload<Mat>{

	public MatPayload() {
		super();
	}

	public MatPayload(Mat value) {
		super(value);
	}

}
