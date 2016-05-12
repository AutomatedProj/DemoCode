package com.test.demo.automationFramework;

import org.testng.annotations.Test;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.classfile.Utility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.test.demo.appModules.Automator;
import com.test.demo.appModules.TestCase1;
import com.test.demo.utility.Constant;
import com.test.demo.utility.ExcelUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

/**
 * The TestSuiteMainMethod Class contains the main method which runs the test
 * suite. The Local object repository is read from the excel sheet and stored in
 * HashMap LocalObjectRepo.
 * 
 * @author
 * @version 1.0
 */

public class TestSuiteMain {

	/**
	 * Initialize map to store Local Object Repository
	 */
	public final static Map<String, String> LocalObjectRepo = new HashMap<String, String>();
	public static ExtentReports extent;
	public static ExtentTest test;

	public static int TotalStep = 0;
	public static int failedStep = 0;
	public static String TestCaseName = "";
	public static int flag = 0;

	// Initialize WebDriver
	private static WebDriver driver = null;
	public static String date = null;

	/**
	 * runTest Method calls action that can be performed by calling
	 * processAction, it reads the excel object locator, finds the element and
	 * then passes the element and the action to processAction method
	 * 
	 * @param driver
	 *            Selenium WebDriver instance
	 */
	private static void runTest(WebDriver driver) throws Exception {
		int i = 1, j;
		String obj = "";
		String path = "";

		while (!ExcelUtils.getCellData(i, 0).isEmpty()) {

			try {
				System.out.println(ExcelUtils.getCellData(i, 0));
				WebElement element = Automator.findElement(driver, ExcelUtils.getCellData(i, 2), LocalObjectRepo);
				switch (ExcelUtils.getCellData(i, 1)) {
				case "verifyOverallStatus":
					j = i;
					i += TestCase1.checkTheContentOfDropDown(driver, element, ExcelUtils.getCellData(i, 1),
							ExcelUtils.getCellData(i, 4), j, test);
					break;
				default:
					Automator.processAction(driver, element, ExcelUtils.getCellData(i, 1),
							ExcelUtils.getCellData(i, 4));
					ExcelUtils.setCellData("Passed", i, 5);
					obj = "";
					if (!ExcelUtils.getCellData(i, 2).isEmpty()) {
						obj = " Object:- " + ExcelUtils.getCellData(i, 2);
					}
					test.log(LogStatus.PASS,
							ExcelUtils.getCellData(i, 0) + " " + ExcelUtils.getCellData(i, 1) + " " + obj, "Passed");
					i++;

				}
			} catch (NoSuchElementException e) {
				failedStep++;
				path = Automator.captureScreenShot(driver);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				test.log(
						LogStatus.FAIL, ExcelUtils.getCellData(i, 0) + " - " + ExcelUtils.getCellData(i, 1)
								+ " - Object: " + ExcelUtils.getCellData(i, 2) + " - Message: No such element",
						"Failed");
				ExcelUtils.setCellData("Failed", i, 5);
				ExcelUtils.setCellData(e.getMessage(), i, 6);
				System.out.println(e.getMessage());
				i++;
			} catch (ElementNotVisibleException e) {
				failedStep++;
				path = Automator.captureScreenShot(driver);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				test.log(
						LogStatus.FAIL, ExcelUtils.getCellData(i, 0) + " - " + ExcelUtils.getCellData(i, 1)
								+ " - Object: " + ExcelUtils.getCellData(i, 2) + " - Message: Element Not Visible",
						"Failed");
				ExcelUtils.setCellData("Failed", i, 5);
				ExcelUtils.setCellData(e.getMessage(), i, 6);
				System.out.println(e.getMessage());
				i++;
			} catch (StaleElementReferenceException e) {
				failedStep++;
				path = Automator.captureScreenShot(driver);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				test.log(
						LogStatus.FAIL, ExcelUtils.getCellData(i, 0) + " - " + ExcelUtils.getCellData(i, 1)
								+ " - Object: " + ExcelUtils.getCellData(i, 2) + " - Message: Stale Element Reference",
						"Failed");
				ExcelUtils.setCellData("Failed", i, 5);
				ExcelUtils.setCellData(e.getMessage(), i, 6);
				System.out.println(e.getMessage());
				i++;
			} catch (TimeoutException e) {
				failedStep++;
				path = Automator.captureScreenShot(driver);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				test.log(LogStatus.FAIL, ExcelUtils.getCellData(i, 0) + " - " + ExcelUtils.getCellData(i, 1)
						+ " - Object: " + ExcelUtils.getCellData(i, 2) + " - Message: Timed Out", "Failed");
				ExcelUtils.setCellData("Failed", i, 5);
				ExcelUtils.setCellData(e.getMessage(), i, 6);
				System.out.println(e.getMessage());
				i++;
			} catch (Exception e) {
				failedStep++;
				path = Automator.captureScreenShot(driver);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				test.log(
						LogStatus.FAIL, ExcelUtils.getCellData(i, 0) + " - " + ExcelUtils.getCellData(i, 1)
								+ " - Object: " + ExcelUtils.getCellData(i, 2) + " - Message: " + e.getMessage(),
						"Failed");
				ExcelUtils.setCellData("Failed", i, 5);
				ExcelUtils.setCellData(e.getMessage(), i, 6);
				System.out.println(e.getMessage());
				i++;
			}

		}
		TotalStep += i - 1;
	}

	@BeforeTest
	public static void extentReportInitialisation() {
		date = new Date().toString();
		date = date.replace(' ', '_');
		date = date.replace(':', '_');
		extent = new ExtentReports(
				"C:\\ExtentReportsOutput\\DEMO\\DEMOtestreport" + date + "\\testreportDEMO" + date + ".html",
				false, DisplayOrder.NEWEST_FIRST, NetworkMode.OFFLINE);
		openLogFile();
	}

	/**
	 * Setup method is called to set the environment up
	 * 
	 * @return Selenium WebDriver
	 * @throws java.lang.Exception
	 *             If excel sheet throws an exception
	 */
	@BeforeMethod
	public static WebDriver setup() throws Exception {

		// For setting the rows color in the report file
		if (flag == 0) {
			flag = 1;
		} else {
			flag = 0;
		}
		// This is to open the Excel file. Excel path, file name and the sheet
		// name are parameters to this method
		// Read the local object repository
		ExcelUtils.setExcelFile(Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, "Local Object Repository");
		Sheet sheet = ExcelUtils.getSelectedSheet();
		try {

			for (Row row : sheet) {
				if (row.getRowNum() == 0) { // if the sheet is empty
					continue;
				}
				LocalObjectRepo.put(row.getCell(0).toString(), row.getCell(1).toString());
			}
		} catch (Exception e) {
			// The work sheet will throw exception when it is read fully
		}
		// Path for Chrome Driver
		String CHROME_DRIVER_PATH = Utility.class.getClassLoader().getResource("chromedriver.exe").getFile();
		// Set path for Chrome driver and Create a new Chrome WebDriver
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS); // wait
																			// 100
																			// seconds
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * method teardown closes the WebDriver
	 */
	@AfterMethod
	public static void teardown() {
		// ending test
		extent.endTest(test);

		// writing everything to document
		extent.flush();
		driver.quit();

		String StringToWrite = "";
		if (flag % 2 == 1) {
			StringToWrite = "<tr style=\"background: #ddedf1;\">"
					+ "<td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ TestCaseName
					+ "</td><td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ failedStep
					+ "</td><td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ TotalStep + "</td></tr>";
		} else {
			StringToWrite = "<tr style=\"background: #BCAAA4;color:#ffffff\">"
					+ "<td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ TestCaseName
					+ "</td><td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ failedStep
					+ "</td><td style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;\">"
					+ TotalStep + "</td></tr>";
		}

		byte data[] = StringToWrite.getBytes();
		Path p = Paths.get(Constant.PATH_LOGFILE);
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, APPEND))) {
			out.write(data, 0, data.length);
		} catch (IOException x) {
			System.err.println(x);
		}
		StringToWrite = "";
		failedStep = 0;
		TotalStep = 0;

	}

	/**
	 * openLogFile method basically creates a log file in which the status of
	 * the Test Suite is being written for each run.
	 * 
	 */
	public static void openLogFile() {
		String TEST_SUITE_NAME = "<h3>Automation Demo</h3>";
		String firstLine = "<table style=\"width:80%;border: 3px inset #1976d2;border-collapse: collapse;\"><tr style=\"color:white;\">"
				+ "<th style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;background: #A6A4A4;\">"
				+ "TestCase Name</th>"
				+ "<th style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;background: #FFB1B1;color:black;\">"
				+ "Failed Steps</th>"
				+ "<th style=\" padding: 5px;text-align: left;border: 2px inset black;border-collapse: collapse;background: #A6A4A4;\">"
				+ "Total Steps</th></tr>";
		byte data[] = TEST_SUITE_NAME.getBytes();
		Path p = Paths.get(Constant.PATH_LOGFILE);
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, TRUNCATE_EXISTING))) {
			out.write(data, 0, data.length);
			data = firstLine.getBytes();
			out.write(data, 0, data.length);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	/**
	 * closeLogFile method closes a log file in which the status of the Test
	 * Suite is being written for each run.
	 */
	@AfterSuite
	public void closeLogFile() {
		String StringToWrite = "</table>";
		byte data[] = StringToWrite.getBytes();
		Path p = Paths.get(Constant.PATH_LOGFILE);
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, APPEND))) {
			out.write(data, 0, data.length);
			System.out.println("Done Writing");
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	/**
	 * testCase1 that runs the test for the first test case
	 * 
	 * @throws java.lang.Exception
	 *             if tests raise any exception due to an issue
	 */

	@Test(description = "Test Case 1", priority = 1)
	public static void testCase1() throws Exception {
		TestCaseName = "Test Case 1";
		// Set the sheet DownloadArena to be used for this test
		test = extent.startTest("Test Case 1");
		System.out.println("Test Case 1");
		ExcelUtils.setExcelFile(Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, "TestCase1");
		// Run the test
		runTest(driver);
		// Save the result to the sheet
		ExcelUtils.saveSheet();
	}

	/**
	 * testCase2 that runs the test for the second test case
	 * 
	 * @throws java.lang.Exception
	 *             if tests raise any exception due to an issue
	 */

	@Test(description = "Test Case 2", priority = 2)
	public static void testCase2() throws Exception {
		TestCaseName = "Test Case 2";

		// Set the sheet DownloadArena to be used for this test
		test = extent.startTest("Test Case 2");
		System.out.println("Test Case 2");
		ExcelUtils.setExcelFile(Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, "TestCase2");
		// Run the test
		runTest(driver);
		// Save the result to the sheet
		ExcelUtils.saveSheet();
	}

	/**
	 * testCase3 that runs the test for the third test case
	 * 
	 * @throws java.lang.Exception
	 *             if tests raise any exception due to an issue
	 */

	@Test(description = "Test Case 3", priority = 3)
	public static void testCase3() throws Exception {
		TestCaseName = "Test Case 3";

		// Set the sheet DownloadArena to be used for this test
		test = extent.startTest("Test Case 3");
		System.out.println("Test Case 3");
		ExcelUtils.setExcelFile(Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, "TestCase3");
		// Run the test
		runTest(driver);
		// Save the result to the sheet
		ExcelUtils.saveSheet();
	}

}