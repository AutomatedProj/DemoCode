package com.test.demo.appModules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
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
	 * checkTheContentOfDropDown methods checks whether all the views are loaded
	 * in the drop down of the choose view or not.
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
	 */
	public static int checkTheContentOfDropDown(WebDriver driver, WebElement element, String action, String value,
			int count, ExtentTest test) {
		int step = count;
		String path = "";
		int substeps = 0;
		String obj[] = new String[substeps+1];
		int cntr=-1;
		try {
			obj[++cntr] = "";
			if (!ExcelUtils.getCellData(step, 2).isEmpty()) {
				obj[cntr] = " Object:- " + ExcelUtils.getCellData(step, 2);
			}
			List<WebElement> ele = element.findElements(By.xpath("./li"));
			String val[] = value.split(",");
			int flag = 0;
			for (int i = 0; i < val.length; i++) {
				flag = 0;
				for (WebElement elem : ele) {
					String text = elem.getText();
					if (text.equalsIgnoreCase(val[i])) {
						flag = 1;
						break;
					}
				}
				if (flag == 0) {
					throw new Exception(".All the values are not there in the choose view drop down:");
				}
			}
			++step;
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, null, path);
		} catch (Exception e) {
			TestSuiteMain.failedStep++;
			path = Automator.captureScreenShot(driver);
			ReportWriting.writeToReport(driver, obj, count, step, test, substeps, e, path);
		}
		return 1;
	}
}