package com.selenium.basic;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Implementation automation = null;
		try (Scanner sc = new Scanner(System.in)) {
			automation = new Implementation();
			System.out.println("Enter 1 for Chrome, 2 for Firefox:");
			int choice = sc.nextInt();

			automation.createDriver(choice);
			automation.handlePopup();
			automation.search();
			automation.sortByPopularity();
			automation.setPrice();
			automation.printHeadphones();

		} catch (Exception e) {
			System.err.println("Automation failed: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (automation != null) automation.closeBrowser();
		}
	}
}
