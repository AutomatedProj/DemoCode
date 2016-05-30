package com.test.demo.appModules;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.demo.automationFramework.TestSuiteMain;
import com.test.demo.utility.Constant;

import org.apache.bcel.classfile.Utility;
import org.apache.commons.io.FileUtils;

/**
 * Automator class is used for automation in the UI, It has method for closing
 * the popup, Method to find elements from the UI and process action on the
 * elements, Method to capture screenshots
 * 
 * @author
 * @version 1.0
 */
public class Automator {

	/**
	 * The method closePopup() is used to close the popups of the browser. This
	 * test involves launching Operating system popups which need to be closed
	 * manually and do not close by this method
	 * 
	 * @param driver
	 *            Webdriver reference
	 */
	private static void closePopup(WebDriver driver) {
		// get window handles
		String mainWindow = driver.getWindowHandle();
		// Wait for more than one window to be created
		new WebDriverWait(driver, 120).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.getWindowHandles().size() == 2;
			}
		});

		// close the window
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(mainWindow)) {
				driver.switchTo().window(handle);
				driver.close();
				break;
			}
		}

		// switch to main window
		driver.switchTo().window(mainWindow);
	}

	/**
	 * highlightElement method is basically used to highlight the element during
	 * the current execution with a yellow background and a border of 2px red
	 * and after five second that highlight things toggles back to its original
	 * state.
	 * 
	 * @param driver
	 *            Selenium Webdriver reference
	 * 
	 * @param element
	 *            UI element reference
	 */
	public static void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style' ,'background: yellow;border:2px solid red')", element);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		js.executeScript("arguments[0].setAttribute('style' ,'border: inherit;')", element);
	}

	/**
	 * Method processAction is used to process the actions on the UI, which is
	 * read from the excel
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
	 * @throws java.lang.Exception
	 *             If an action leads to an exception
	 */
	public static void processAction(WebDriver driver, WebElement element, String action, String value)
			throws Exception {
		switch (action) {
		case "Enter":
			highlightElement(driver, element);
			if (value.equals("ENTER")) {
				element.sendKeys(Keys.ENTER);
				return;
			}
			element.sendKeys(value);
			break;
		case "login":
			driver.get(value);
			String PATH_AUTOIT = Utility.class.getClassLoader().getResource("authentication.exe").getFile();
			Runtime.getRuntime().exec(PATH_AUTOIT);
			break;
		case "click":
			highlightElement(driver, element);
			element.click();
			break;
		case "clear":
			highlightElement(driver, element);
			element.clear();
			break;
		case "close":
			closePopup(driver);
			break;
		case "goback":
			driver.navigate().back();
			break;
		case "navigate":
			driver.navigate().to(Constant.BASEURL + value);
			break;
		case "selectValue":
			highlightElement(driver, element);
			new Select(element).selectByVisibleText(value);
			break;
		case "get":
			driver.get(value);
			break;
		case "wait":
			try {
				Thread.sleep(Long.parseLong(value));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "verify":
			highlightElement(driver, element);
			if (!value.equals(element.getText())) {
				throw new Exception("Does not match - Expected: " + value + " Observed: " + element.getText());
			}
			break;
		case "verifyPartialText":
			highlightElement(driver, element);
			if (!element.getText().contains(value)) {
				throw new Exception("Does not match");
			}
			break;
		case "verifyValue":
			highlightElement(driver, element);
			if (!value.equals(element.getAttribute("value"))) {
				throw new Exception(
						"Value does not match - Expected: " + value + " Observed: " + element.getAttribute("value"));
			}
			break;
		case "move":
			highlightElement(driver, element);
			Actions builder = new Actions(driver);
			builder.moveToElement(element);
			break;
		case "exists":
			highlightElement(driver, element);
			if (!(element.isDisplayed())) {
				throw new Exception("Does not exist");
			}
			break;
		case "isEnabled":
			highlightElement(driver, element);
			if (!(element.isEnabled())) {
				throw new Exception("Does not exist");
			}
			break;
		case "isDisabled":
			highlightElement(driver, element);
			if (element.isEnabled())
				throw new Exception("The element should be disabled.");
			break;
		case "isAriaDisabled":
			highlightElement(driver, element);
			if (element.getAttribute("aria-disabled") == null) {
				throw new Exception("The element should be disabled.");
			}
			break;
		case "isAriaEnabled":
			highlightElement(driver, element);
			if (!(element.getAttribute("aria-disabled") == null)) {
				throw new Exception("The element should be enabled");
			}
			break;
		case "hover":
			highlightElement(driver, element);
			builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			break;
		case "contains":
			highlightElement(driver, element);
			Assert.assertTrue(element.getText().toLowerCase().contains(value.toLowerCase()));
			break;
		case "refresh":
			driver.navigate().refresh();
			break;
		case "moveAndClick":
			String data[] = value.split(",");
			builder = new Actions(driver);
			builder.moveByOffset(Integer.parseInt(data[0]), Integer.parseInt(data[1])).click().perform();
			break;
		case "verifyTitle":
			if (!value.equals(element.getAttribute("title"))) {
				throw new Exception("Pass/Fail icon not visible");

			}
			break;
		case "verifyPageTitle":
			if (!value.equals(driver.getTitle())) {
				throw new Exception("Page Title does not match");
			}
			break;
		case "openNewTab":
			builder = new Actions(driver);
			builder.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			break;
		case "switchTab":
			tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(tabs2.size() - 1));
			break;
		case "closeTab":
			tabs2 = new ArrayList<String>(driver.getWindowHandles());
			if (tabs2.size() == 2) {
				driver.close();
			}
			break;
		case "verifyDownload":
			String url = element.getAttribute("href");
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				throw new Exception("Download file failed");
			}
			break;
		case "clickAndHold":
			highlightElement(driver, element);
			String val[] = value.split(",");
			builder = new Actions(driver);
			builder.clickAndHold(element);
			builder.moveByOffset(Integer.parseInt(val[0]), Integer.parseInt(val[1])).perform();
			break;
		case "clickRelease":
			builder = new Actions(driver);
			builder.release().perform();
			break;
		case "scrollForThePage":
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, window.innerHeight)");
			break;
		case "scrollForEle":
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			break;
		case "clearCache":
			builder = new Actions(driver);
			builder.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("r").keyUp(Keys.CONTROL).keyUp(Keys.SHIFT)
					.perform();
			Thread.sleep(5000);
			break;
		case "scrollMainWindow":
			js = (JavascriptExecutor) driver;
			int va = Integer.parseInt(value);
			js.executeScript("window.scrollBy(0," + va + ")", "");
			break;
		case "scrollToBottom":
			builder = new Actions(driver);
			builder.sendKeys(Keys.END).perform();
			break;
		case "scrollToTop":
			builder = new Actions(driver);
			builder.sendKeys(Keys.HOME).perform();
			break;
		case "maximizeWindow":
			driver.manage().window().maximize();
			break;
		default:
			throw new Exception("No Action to be processed");
		}
	}

	/**
	 * findElement method is used to find the element from the UI using
	 * locators,first the element is searched in the local object repository and
	 * then locator is parsed and the the ui element is searched
	 * 
	 * @param driver
	 *            Is the Selenium Webdriver instance
	 * 
	 * @param value
	 *            The locator provided in the excel sheet
	 * 
	 * @param localObjectRepo
	 *            Local object repository read fronm the excel sheet
	 * 
	 * @return element WebElement
	 */
	public static WebElement findElement(WebDriver driver, String value, Map<String, String> localObjectRepo) {
		if (!localObjectRepo.containsKey(value)) {
			return null;
		}
		String[] values = localObjectRepo.get(value).split("==");
		if ("id".equals(values[0])) {
			try {
				return driver.findElement(By.id(values[1]));
			} catch (Exception e) {
				return null;
			}
		}
		if ("linkText".equals(values[0])) {
			return driver.findElement(By.linkText(values[1]));
		}
		if ("partialLinkText".equals(values[0])) {
			return driver.findElement(By.partialLinkText(values[1]));
		}
		if ("css".equals(values[0])) {
			return driver.findElement(By.cssSelector(values[1]));
		}
		if ("xpath".equals(values[0])) {
			WebElement ele = new WebDriverWait(driver, 10) // 10 seconds until
															// present
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(values[1])));
			return ele;
		}

		return null;
	}

	/**
	 * findElements method is used to find the multiple elements from the UI
	 * using locators,first the composite element path is searched in the local
	 * object repository and then locator is parsed and the the ui element is
	 * searched
	 * 
	 * @param driver
	 *            Is the Selenium Webdriver instance
	 * 
	 * @param value
	 *            The locator provided in the excel sheet
	 * 
	 * @param localObjectRepo
	 *            Local object repository read fronm the excel sheet
	 * 
	 * @return element WebElement
	 */

	public static List<WebElement> findElements(WebDriver driver, String value, Map<String, String> localObjectRepo) {
		if (!localObjectRepo.containsKey(value)) {
			return null;
		}
		String[] values = localObjectRepo.get(value).split("==");
		if ("id".equals(values[0])) {
			try {
				return driver.findElements(By.id(values[1]));
			} catch (Exception e) {
				return null;
			}
		}
		if ("linkText".equals(values[0])) {
			return driver.findElements(By.linkText(values[1]));
		}
		if ("partialLinkText".equals(values[0])) {
			return driver.findElements(By.partialLinkText(values[1]));
		}
		if ("css".equals(values[0])) {
			return driver.findElements(By.cssSelector(values[1]));
		}
		if ("xpath".equals(values[0])) {
			List<WebElement> ele = new WebDriverWait(driver, 10) // 10 seconds
																	// until
					// present
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(values[1])));
			return ele;
		}

		return null;
	}

	/**
	 * captureScreenShot is used to capture a screenshot of the current screen
	 * 
	 * @param driver
	 *            Is the Selenium Webdriver instance
	 * 
	 */
	public static String captureScreenShot(WebDriver driver) {
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "";
		try {
			// now copy the screenshot to desired location using copyFile method

			path = Constant.PATH_SCREENSHOT + System.currentTimeMillis() + ".png";

			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return path;
	}

}
