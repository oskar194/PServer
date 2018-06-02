package com.admin.budgetrook.managers;

import java.io.File;

public class PipelineManager {
	private OpenCVManager ocvManager = null;
	private TesseractManager tessManager = null;
	
	public PipelineManager() {
		try {
			ocvManager = new OpenCVManager();
			tessManager = new TesseractManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String process(File source, File destination) {
		File partial = ocvManager.process(source, destination);
		return tessManager.doOcr(partial);
//		return "SKIPPED OCR";
	}
}
