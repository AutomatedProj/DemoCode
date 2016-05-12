package com.test.demo.utility;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.demo.automationFramework.TestSuiteMain;

/**
 * The class contains all the methods which deals with writing to the reports
 * and excel file if test cases and its steps have passed/failed
 * 
 * @author
 * @version 1.0
 * 
 */

public class ReportWriting {

	/**
	 * writeResultExcell is called to write into the Excell
	 * 
	 * @param value
	 *            it can be passed and failed
	 * 
	 * @param row
	 *            row number
	 * 
	 * @param cell
	 *            Column Number
	 */

	public static void writeResultExcell(String value, int row, int cell) {
		try {
			ExcelUtils.setCellData(value, row, cell);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * showSkippedMsg function write the Skipped status in the excel file for
	 * the steps which are skipped.
	 * 
	 * @param curStep
	 *            curStep specifies the current Step.
	 * 
	 * @param totStep
	 *            totStep specifies the total no of steps.
	 */
	public static void showskippedMsg(int curStep, int totStep) {

		for (int i = curStep; i < totStep; i++)
			writeResultExcell("Skipped", i, 5);
	}

	/**
	 * writeToReport function is used to write the status of steps that is
	 * passed, failed or skipped to the report and the excel file after the
	 * execution of the actions
	 * 
	 ** @param driver
	 *            Selenium WebDriver reference
	 * 
	 * @param obj
	 *            An array consisting of the objects that the calling function
	 *            deals with
	 * 
	 * @param count
	 *            Step Number of the calling function
	 * 
	 * @param step
	 *            Denotes the current step number at which this function is
	 *            called
	 * 
	 * @param test
	 *            test is the reference to the ExtentTest test that is running
	 *            the test to generate the report
	 * 
	 * @param substeps
	 *            Specifies the number of sub-steps for a function
	 * 
	 * @param e
	 *            Denotes the exception if exists else null in case function has
	 *            passed
	 * 
	 * @param path
	 *            Specifies the path of the screenshot
	 * 
	 */

	public static void writeToReport(WebDriver driver, String obj[], int count, int step, ExtentTest test, int substeps,
			Exception e, String path) {
		if (step == (count + (substeps + 1))) {
			writeResultExcell("Passed", count, 5);
			test.log(LogStatus.PASS,
					ExcelUtils.getCellData(count, 0) + " " + ExcelUtils.getCellData(count, 1) + " " + obj[0], "Passed");
			if (substeps != 0) {
				if (substeps != 1) {
					test.log(LogStatus.INFO, (count + 1) + " to " + (count + substeps), "Sub Steps of " + count);
				} else {
					test.log(LogStatus.INFO, (count + 1) + "", "Sub Step of " + count);
				}
			}
			int j = 0;
			for (int i = count + 1; i <= count + substeps; i++) {
				j++;
				writeResultExcell("Passed", i, 5);
				test.log(LogStatus.PASS,
						ExcelUtils.getCellData(i, 0) + " " + ExcelUtils.getCellData(i, 1) + " " + obj[j], "Passed");
			}
		} else {
			String errormsgClass = new String(e.toString());
			if (errormsgClass.contains(":")) {
				errormsgClass = errormsgClass.substring(0, errormsgClass.indexOf(":"));
			}
			errormsgClass = errormsgClass.substring(errormsgClass.lastIndexOf(".") + 1);
			if(errormsgClass.equals("Exception")){
				String msg = e.getMessage();
				if(msg.contains(".")&&msg.charAt(msg.length()-1)==':'){
					msg = msg.substring(msg.indexOf(".")+1,msg.length()-1);	
				}
				errormsgClass = msg;
			}
			int j = 0;
			writeResultExcell("Failed", count, 5);
			writeResultExcell(errormsgClass, count, 6);
			System.out.println(errormsgClass);
			if (step == count) {
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
			}
			test.log(LogStatus.FAIL, ExcelUtils.getCellData(count, 0) + " " + ExcelUtils.getCellData(count, 1) + " "
					+ obj[0] + " Message: " + errormsgClass, "Failed");
			if (substeps != 0) {
				if (substeps != 1)
					test.log(LogStatus.INFO, (count + 1) + " to " + (count + substeps), "Sub Steps of " + count);
				else
					test.log(LogStatus.INFO, (count + 1) + " ", "Sub Step of " + count);
			}
			for (int i = count + 1; i < step; i++) {
				j++;
				writeResultExcell("Passed", i, 5);
				test.log(LogStatus.PASS,
						ExcelUtils.getCellData(i, 0) + " " + ExcelUtils.getCellData(i, 1) + " " + obj[j], "Passed");
			}
			if (step != count) {
				TestSuiteMain.failedStep++;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(path));
				writeResultExcell("Failed", step, 5);
				writeResultExcell(errormsgClass, step, 6);
				test.log(LogStatus.FAIL,
						ExcelUtils.getCellData(step, 0) + " " + ExcelUtils.getCellData(step, 1) + " "+ obj[++j] + " Message: " + errormsgClass,
						"Failed");
			}
			if (step != (count + substeps)) {
				showskippedMsg(step + 1, count + substeps + 1);
				if ((step + 1) != (count + substeps)) {
					System.out.println((step + 1) + " to " + (count + substeps) + " Skipped ");
					test.log(LogStatus.INFO, (step + 1) + " to " + (count + substeps), "Skipped");
				} else {
					System.out.println((step + 1) + " Skipped ");
					test.log(LogStatus.INFO, (step + 1) + " ", "Skipped");
				}
			}
		}

	}
}
