package com.tianya.springboot.capture.utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

public class CaptureScreenUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CaptureScreenUtils.class);
	
	/**
	 * 捕获屏幕，生成图片
	 */
	public static String capture() {
		
		
		System.setProperty("java.awt.headless", "false");
		
		// 获取屏幕大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		String fileName = null ;
		try {
			
			Robot robot = new Robot();
			Rectangle screenRect = new Rectangle(screenSize);
			// 捕获屏幕
			BufferedImage screenCapture = robot.createScreenCapture(screenRect);
			//存放位置
			String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
			fileName = System.currentTimeMillis() + ".png" ;
			LOGGER.info("屏幕截屏路径：{}", path);
			// 把捕获到的屏幕 输出为 图片
			ImageIO.write(screenCapture, "png", new File(path +File.separator + fileName));
			
		} catch (Exception e) {
			LOGGER.error("获取屏幕截屏发生异常", e);
		}
		
		return fileName ;
	}
	
	

}
