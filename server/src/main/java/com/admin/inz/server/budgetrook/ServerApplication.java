package com.admin.inz.server.budgetrook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import org.apache.commons.io.IOUtils;
import org.opencv.core.Core;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}

	public static void main(String[] args) {
		configureApplication(new SpringApplicationBuilder()).run(args);
	}

	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(ServerApplication.class).bannerMode(Banner.Mode.OFF);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	static {
		try {
			loadTesseractLib();
			loadOpenCVLib();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadTesseractLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String ver;
		String libraryPath = "src/main/resources/tess4j/win32-x86/";
		ver = "32";
		if (model.equals("64")) {
			libraryPath = "src/main/resources/tess4j/win32-x86-64/";
			ver = "64";
		}
		System.setProperty("java.library.path", libraryPath);
		Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
		sysPath.setAccessible(true);
		sysPath.set(null, null);
		System.loadLibrary("gsdll" + ver);
		System.loadLibrary("liblept1744");
		System.loadLibrary("libtesseract3051");
	}
	
	private static void loadOpenCVLib() throws Exception {
		String model = System.getProperty("sun.arch.data.model");
		String libraryPath = "src/main/resources/opencv/x86/";
		if (model.equals("64")) {
			libraryPath = "src/main/resources/opencv/x64/";
		}
		System.setProperty("java.library.path", libraryPath);
		Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
		sysPath.setAccessible(true);
		sysPath.set(null, null);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
