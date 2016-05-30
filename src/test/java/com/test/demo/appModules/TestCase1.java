package com.test.demo.appModules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.test.demo.appModules.Automator;
import com.test.demo.automationFramework.TestSuiteMain;
import com.test.demo.utility.ExcelUtils;
import com.test.demo.utility.ReportWriting;

/**
 * The class contains all the methods which deals with the ChooseView test case.
 * 
 * @author
 * @version 1.0
 * 
 */

public class TestCase1 {

	/**
	 * verifyCountOfErrorDivs method checks if on clicking the submit button
	 * without filling in any data, shows the required fields with errors and
	 * matches their count with what is provided in the excel *
	 * 
	 * @param driver
	 *            Selenium WebDriver reference
	 * 
	 * @param element
	 *            UI element reference
	 * 
	 * @param action
	 *            The action like click, Enter etc.
	 * 
	 * @param value
	 *            Value to be passed for navigate or enter in a input field
	 * 
	 * @param count
	 *            Count specify the step when this function is called
	 * 
	 * @param test
	 *            test is the reference to the ExtentTest test that is running
	 *            the test to generate the report
	 * @return returns the number of the steps to be moved in the excel i.e
	 *         number of sub steps +1.
	 */
	public static int verifyCountOfErrorDivs(WebDriver driver, WebElement element, String action, String value,
			int count, ExtentTest test) {
		int step = count;
		String path = "";
		int substeps = 1;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		try {
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}

			System.out.println(++step);
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}
			List<WebElement> divs = Automator.findElements(driver, ExcelUtils.getCellData(step, 2),
					TestSuiteMain.LocalObjectRepo);
			int val = Integer.parseInt(value);
			if (divs.size() != val) {
				throw new Exception("Error message for required fields not shown as expected");
			}
			++step;
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, path);
		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 2;
	}

	/**
	 * Method clickRadioButtonWithGivenValue is used to click on the radio
	 * button with the value mentioned in the Excel after find the list of radio
	 * buttons specified in excel
	 * 
	 * @param driver
	 *            Selenium WebDriver reference
	 * 
	 * @param element
	 *            UI element reference
	 * 
	 * @param action
	 *            The action like click, Enter etc.
	 * 
	 * @param value
	 *            Value to be passed for navigate or enter in a input field
	 * 
	 * @param count
	 *            Count specify the step when this function is called
	 * 
	 * @param test
	 *            test is the reference to the ExtentTest test that is running
	 *            the test to generate the report
	 * @return the number of steps to be moved in excel
	 * 
	 */

	public static int clickRadioButtonWithGivenValue(WebDriver driver, WebElement element, String action, String value,
			int count, ExtentTest test) {
		int step = count;
		String path = "";
		int flag = 0;
		int substeps = 2;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		try {
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}

			System.out.println(++step);
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}
			List<WebElement> rblist = Automator.findElements(driver, ExcelUtils.getCellData(step, 2),
					TestSuiteMain.LocalObjectRepo);
			// rblist has all the radio buttons
			// for loop traverses through the radio buttons to find the match
			for (WebElement cur : rblist) {
				String val = cur.getAttribute("value");
				if (val.contains(value)) {
					flag = 1;
					System.out.println(++step);
					obj[++cntr] = "Object:- radiobutton" + value;
					if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
						obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
					}
					Automator.processAction(driver, cur, ExcelUtils.getCellData(step, 1), value);
					++step;
					ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, "");

					break;
				}
			}
			if (flag == 0) {
				throw new Exception(".No Radio Buttons with the given value:");
			}

		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 3;
	}

	/**
	 * Method clickCheckboxWithGivenValue is used to click on the check box with
	 * the value mentioned in the Excel after find the list of check boxes
	 * specified in excel
	 * 
	 * @param driver
	 *            Selenium WebDriver reference
	 * 
	 * @param element
	 *            UI element reference
	 * 
	 * @param action
	 *            The action like click, Enter etc.
	 * 
	 * @param value
	 *            Value to be passed for navigate or enter in a input field
	 * 
	 * @param count
	 *            Count specify the step when this function is called
	 * 
	 * @param test
	 *            test is the reference to the ExtentTest test that is running
	 *            the test to generate the report
	 * @return the number of steps to be moved in excel
	 * 
	 */

	public static int clickCheckboxWithGivenValue(WebDriver driver, WebElement element, String action, String value,
			int count, ExtentTest test) {
		int step = count;
		String path = "";
		int flag = 0;
		int substeps = 2;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		try {
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}

			System.out.println(++step);
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}
			List<WebElement> cblist = Automator.findElements(driver, ExcelUtils.getCellData(step, 2),
					TestSuiteMain.LocalObjectRepo);
			// cblist has all the check boxes
			// for loop traverses through the check boxes to find the match
			for (WebElement cur : cblist) {
				String val = cur.getAttribute("value");
				if (val.contains(value)) {
					flag = 1;
					System.out.println(++step);
					obj[++cntr] = "Object:- checkbox" + value;
					if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
						obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
					}
					Automator.processAction(driver, cur, ExcelUtils.getCellData(step, 1), value);
					++step;
					ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, "");

					break;
				}
			}
			if (flag == 0) {
				throw new Exception(".No Check boxes with the given value:");
			}

		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 3;
	}
}