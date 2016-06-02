package com.test.demo.appModules;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.test.demo.appModules.Automator;
import com.test.demo.automationFramework.TestSuiteMain;
import com.test.demo.utility.ExcelUtils;
import com.test.demo.utility.ReportWriting;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

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

	/**
	 * dragNDrop method basically deals with the DragNDrop operation which click
	 * and hold one element and drops that element in the destination container.
	 * 
	 * @param driver
	 *            Selenium Webdriver reference
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
	 * @return returns the number of the test cases of the composite test cases.
	 * 
	 */
	public static int dragNDrop(WebDriver driver, WebElement element, String action, String value, int count,
			ExtentTest test) {
		int step = count;
		int substeps = 1;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		String path = "";
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
			WebElement destination = Automator.findElement(driver, ExcelUtils.getCellData(step, 2),
					TestSuiteMain.LocalObjectRepo);
			Actions builder = new Actions(driver);
			builder.dragAndDrop(element, destination).perform();
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
	 * selectFromDropDwnClick Method is basically used for selecting the values
	 * from the drop down.And the values are fetched from the excel file.
	 * 
	 * @param driver
	 *            Selenium Webdriver reference
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
	 * 
	 * @return This method return the no of the sub-steps are for this composite
	 *         testcase
	 * 
	 */

	public static int selectFromDropDwnClick(WebDriver driver, WebElement element, String action, String value,
			int count, ExtentTest test) throws Exception {
		int step = count;
		String path = "";
		int substeps = 0;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		try {

			int flag = 0;
			List<WebElement> ele = element.findElements(By.xpath(".//li"));
			for (WebElement elem : ele) {
				String time = elem.getAttribute("innerText");
				if (time.contains(value)) {
					flag = 1;
					Automator.processAction(driver, elem, "click", value);
					break;
				}
			}
			if (flag == 1) {
				obj[++cntr] = "";
				if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
					obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
				}
				++step;
				ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, "");

			} else {
				throw new Exception(".The specified value is not there in the list:");
			}
		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 1;
	}

	/**
	 * validateDatePickedmethod basically checks whether the date in the
	 * provided input is 15 of next month or not.
	 * 
	 * @param driver
	 *            Selenium Webdriver reference
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
	 * 
	 * @return This method return the no of the sub-steps are for this composite
	 *         testcase
	 * 
	 */

	public static int validateDatePicked(WebDriver driver, WebElement element, String action, String value, int count,
			ExtentTest test) throws Exception {
		int step = count;
		String path = "";
		int substeps = 0;
		String obj[] = new String[substeps + 1];
		int cntr = -1;
		try {

			String datefetched = element.getAttribute("value");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH,15);
			calendar.add(Calendar.MONTH,1);
			Date date = calendar.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String dateCalculated = dateFormat.format(date);
			if (datefetched.equals(dateCalculated)) {
				obj[++cntr] = "";
				if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
					obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
				}
				++step;
				ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, "");

			} else {
				throw new Exception("The correct date is not picked!!!");
			}
		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 1;
	}
}