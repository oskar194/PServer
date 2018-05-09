package com.admin.budgetrook.pipeline.command;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import com.admin.budgetrook.pipeline.input.MatPayload;

public class ExtractBiggestCommand implements Command<MatPayload> {

	public MatPayload execute(MatPayload input) {
		Mat src = input.getValue();
		List<Rect> boundingRect = CommandHelper.detectLettersBounding(src);
		Rect max = Collections.max(boundingRect, new CompRect());
		if (max != null) {
			// if(boundingRect.remove(max)) {
			// Rect max2 = Collections.max(boundingRect, new CompRect());
			// drawRectangleWithLines(src, max2, new Scalar(255,255,255), 5);
			// }
			CommandHelper.drawRectangleWithLines(src, max, new Scalar(255, 255, 255), 5);
		}
		input.setValue(src);
		return input;
	}

	private class CompRect implements Comparator<Rect> {
		public int compare(Rect a, Rect b) {
			if (a.height > b.height)
				return 1;
			if (a.height == b.height)
				return 0;
			return -1;
		}
	}

}
