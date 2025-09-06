package com.selenium.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileIO {

	private Properties prop;

	/** Load properties from config file */
	public Properties inputSetup() {
		if (prop == null) {
			prop = new Properties();
			File file = new File("resources\\config.properties");
			try (FileInputStream fis = new FileInputStream(file)) {
				prop.load(fis);
			} catch (IOException e) {
				System.err.println("Error loading config.properties");
				e.printStackTrace();
			}
		}
		return prop;
	}

	/** Get URL from config */
	public String getURL() {
		inputSetup();
		return prop.getProperty("url");
	}

	/** Write headphone data to Excel */
	public void output(String[] names, String[] prices) {
		String fileName = "output_" + System.currentTimeMillis() + ".xlsx";

		try (XSSFWorkbook workbook = new XSSFWorkbook();
			 FileOutputStream fos = new FileOutputStream(fileName)) {

			XSSFSheet sheet = workbook.createSheet("Headphones");

			// Header
			XSSFRow header = sheet.createRow(0);
			header.createCell(0).setCellValue("Headphone Name");
			header.createCell(1).setCellValue("Price");

			// Data rows
			for (int i = 0; i < names.length; i++) {
				XSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(names[i]);
				row.createCell(1).setCellValue(prices[i]);
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);

			workbook.write(fos);
			System.out.println("Excel file created: " + fileName);

		} catch (IOException e) {
			System.err.println("Error writing Excel file");
			e.printStackTrace();
		}
	}
}
