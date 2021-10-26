package com.hubspot.keyFramwork.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hubspot.keywordFramework.base.BaseClass;

public class KeywordEngine {

	public WebDriver driver;
	public Properties property;
	public BaseClass baseObject;
	public static Workbook workbook;
	public static Sheet sheet;

	public final String TESTSCENARIO_PATH = "C:\\Users\\DEV\\eclipse-workspace\\automation\\src\\main\\java\\com\\hubspot\\keywordFramework\\scenarios\\TestScenario.xlsx";

	public void startExecution(String sheetName) {
		
		String locatorName = null;
		String locator = null;
		FileInputStream file = null;

		try {
			file = new FileInputStream(TESTSCENARIO_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			workbook = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sheet = workbook.getSheet(sheetName);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
			String locatorsColumn = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
			if (!locatorsColumn.equalsIgnoreCase("NA")) {
				locatorName = locatorsColumn.split("=")[0].trim();
				locator = locatorsColumn.split("=")[1].trim();
			}
			String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
			String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

			switch (action) {
			case "launch browser":
				baseObject = new BaseClass();
				property = baseObject.init_properties();
				if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
					driver = baseObject.init_driver(property.getProperty(locator));
				} else {
					driver = baseObject.init_driver(value);
				}
				break;
			case "launch baseURL":
				property = baseObject.init_properties();
				Thread.sleep(5000L);
				if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
					driver.get(property.getProperty("baseURL"));
					Thread.sleep(5000L);
				} else {
					driver.get(value);
					Thread.sleep(50000L);
				}
				break;
			case "quit":
				driver.quit();
				break;
			default:
				break;
			}
			
		
			switch (locatorName) {
			case "id":
				WebElement element=driver.findElement(By.id(locator));		
				if(action.equalsIgnoreCase("sendkeys")) {
					element.clear();
					element.sendKeys(value);
				}
				else if(action.equalsIgnoreCase("click")) {
					element.click();
				}
				locatorName=null;
				break;

			default:
				break;
			}
			
			}
			catch(Exception e) {
				//System.out.println(e);
			}
		}
		

	}

}
