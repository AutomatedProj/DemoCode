package com.test.demo.utility;

import com.test.demo.automationFramework.TestSuiteMain;

/**
 * Constant class to take care of constants used in the Project
 * @author 
 * @version 1.0
 */
public class Constant {

	// provide the path for the spreadsheet
	public static final String PATH_TESTDATA = "src/test/java/testData/";
	// provide the name of the spreadsheet
	public static final String FILE_TESTDATA = "DemoTestSuite.xlsx";
	// provide the base URL for the application
	public static final String BASEURL = "http://demoqa.com/";
	// Path for Screenshots
	public static final String PATH_SCREENSHOT = "C:\\ExtentReportsOutput\\DEMO\\DEMOtestreport"
			+ TestSuiteMain.date + "\\Screenshots\\screenshot";
	// Path for creating the log file
	public static final String PATH_LOGFILE = "C:\\ExtentReportsOutput\\DEMO\\DEMOtestreport" + TestSuiteMain.date
			+ "\\DEMOLogFile.html";
}
