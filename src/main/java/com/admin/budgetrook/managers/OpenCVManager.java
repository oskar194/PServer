package com.admin.budgetrook.managers;

import java.io.File;
import java.lang.reflect.Field;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.admin.budgetrook.pipeline.command.BinarizeCommand;
import com.admin.budgetrook.pipeline.command.BoldenTextCommand;
import com.admin.budgetrook.pipeline.command.DenoiseCommand;
import com.admin.budgetrook.pipeline.command.DeskewCommand;
import com.admin.budgetrook.pipeline.command.DetectTextBlocksCommand;
import com.admin.budgetrook.pipeline.command.ExtractBiggestRectangleCommand;
import com.admin.budgetrook.pipeline.command.ExtractCashCommand;
import com.admin.budgetrook.pipeline.input.MatPayload;
import com.admin.budgetrook.pipeline.manager.Pipeline;

public class OpenCVManager {
	private Pipeline<MatPayload> processingPipeline;

	/*
	 * processingPipeline .add(new BinarizeCommand()) .add(new BoldenTextCommand())
	 * .add(new DetectTextBlocksCommand()) .add(new DenoiseCommand()) .add(new
	 * ExtractBiggestRectangleCommand()) // .add(new DeskewCommand());
	 */
	public OpenCVManager() throws Exception {
		loadOpenCVLib();
		processingPipeline = new Pipeline<MatPayload>();
		processingPipeline
		.add(new BinarizeCommand())
		.add(new DetectTextBlocksCommand())
		.add(new DenoiseCommand())
		.add(new ExtractBiggestRectangleCommand())
		.add(new ExtractCashCommand())
		.add(new ExtractBiggestRectangleCommand())
		.add(new BoldenTextCommand());
//		.add(new DeskewCommand());
	}

	public File process(File source, File destination) {
		Mat src = loadImage(source);
		MatPayload output = new MatPayload();
		output = processingPipeline.executePipeline(new MatPayload(src));
		saveFile(output.getValue(), destination.getAbsolutePath());
		return destination;
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

	private Mat loadImage(File f) {
		Mat src = Imgcodecs.imread(f.getPath(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
		return src;
	}

	private void saveFile(Mat src, String path) {
		Imgcodecs.imwrite(path, src);
	}
}
