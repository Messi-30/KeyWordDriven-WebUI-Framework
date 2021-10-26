package com.hubspot.keywordFramework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClass {

	public WebDriver driver;
	public Properties property;

	public WebDriver init_driver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Data\\Setups\\ChromeDriver\\chromedriver.exe");
			if (property.getProperty("headless").equals("yes")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver = new ChromeDriver(options);
			} else {
				driver = new ChromeDriver();
			}
		}
		return driver;
	}

	public Properties init_properties() {
		property = new Properties();
		try {
			FileInputStream configfile = new FileInputStream(
					"C:\\Users\\DEV\\eclipse-workspace\\automation\\src\\main\\java\\com\\hubspot\\keywordFramework\\config\\config.properties");
			property.load(configfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property;
	}
}
