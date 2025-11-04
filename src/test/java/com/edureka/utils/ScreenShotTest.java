package com.edureka.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShotTest {
	

	public static String capture(WebDriver driver, String filename){

		String timestamp= new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		String path= System.getProperty("user.dir")+"/target/Screenshots/" + filename + timestamp+ ".png";
		
		File source=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
 
}
