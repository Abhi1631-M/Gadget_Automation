package com.selenium.basic;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Implementation {
	private WebDriver driver;
	private Properties prop;
	private FileIO fileio;

	public Implementation() {
		fileio = new FileIO();
		prop = fileio.inputSetup();
	}

	public void createDriver(int choice) {
		driver = DriverSetup.getDriver(choice);
	}

	public void handlePopup() {
		try {
			By popupClose = By.xpath("//div[@id='web_push_pop']//div[contains(@class,'close-button')]");
			WebElement closeBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
					.until(ExpectedConditions.elementToBeClickable(popupClose));
			closeBtn.click();
		} catch (TimeoutException e) {
			System.out.println("No popup appeared.");
		}
	}

	public void search() {
		driver.findElement(By.id(prop.getProperty("searchBox")))
				.sendKeys(prop.getProperty("searchData"));
		driver.findElement(By.xpath(prop.getProperty("searchButton"))).click();
	}

	public void sortByPopularity() {
		By sortDropdown = By.xpath(prop.getProperty("sortDropdown"));
		By popularityOption = By.xpath(prop.getProperty("popularityOption"));

		WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(sortDropdown));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

		WebElement popular = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(popularityOption));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", popular);
	}

	public void setPrice() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement minInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty("minPrice"))));
		minInput.clear();
		minInput.sendKeys(prop.getProperty("minData"));

		WebElement maxInput = driver.findElement(By.name(prop.getProperty("maxPrice")));
		maxInput.clear();
		maxInput.sendKeys(prop.getProperty("maxData"));

		WebElement goBtn = driver.findElement(By.xpath(prop.getProperty("priceGoButton")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBtn);

		// Wait until page reloads
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
	}

	public void printHeadphones() {
		List<WebElement> titles = driver.findElements(By.xpath("//p[@class='product-title']"));
		List<WebElement> prices = driver.findElements(By.xpath("//span[contains(@class,'product-price')]"));

		System.out.println("Top 5 Bluetooth Headphones:");
		for (int i = 0; i < 5 && i < titles.size() && i < prices.size(); i++) {
			System.out.printf("%d. %s - %s%n", i + 1, titles.get(i).getText(), prices.get(i).getText());
		}

		// Optional: write to Excel
		String[] nameArr = titles.stream().limit(5).map(WebElement::getText).toArray(String[]::new);
		String[] priceArr = prices.stream().limit(5).map(WebElement::getText).toArray(String[]::new);
		fileio.output(nameArr, priceArr);
	}

	public void closeBrowser() {
		if (driver != null) driver.quit();
	}
}
