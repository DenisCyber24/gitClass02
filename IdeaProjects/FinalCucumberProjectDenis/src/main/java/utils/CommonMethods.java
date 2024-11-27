package utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonMethods {

    public static WebDriver driver;

    // Open Browser and Launch Application
    public static void openBrowserAndLaunchApplication(String browser, String url) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser);
        }
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    // Close Browser
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Send Text
    public static void sendText(WebElement element, String textToSend) {
        element.clear();
        element.sendKeys(textToSend);
    }

    // Waits
    public static WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void waitForElementToBeClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    // Alerts
    public static void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public static void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public static String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public static void sendTextToAlert(String text) {
        driver.switchTo().alert().sendKeys(text);
    }

    // Dropdowns
    public static void selectDropdownByVisibleText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);
    }

    public static void selectDropdownByValue(WebElement element, String value) {
        new Select(element).selectByValue(value);
    }

    public static void selectDropdownByIndex(WebElement element, int index) {
        new Select(element).selectByIndex(index);
    }

    // Radio Buttons and Checkboxes
    public static void clickRadioButtonOrCheckbox(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        }
    }

    // Window Handles
    public static void switchToWindow(String windowTitle) {
        String mainWindowHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(windowTitle)) {
                return;
            }
        }
        driver.switchTo().window(mainWindowHandle);
    }

    // Frames
    public static void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    public static void switchToFrameByIdOrName(String idOrName) {
        driver.switchTo().frame(idOrName);
    }

    public static void switchToFrameByElement(WebElement element) {
        driver.switchTo().frame(element);
    }

    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // Screenshots
    public static void takeScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(fileName));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(CommonMethods.class.getName());
            logger.log(Level.SEVERE, "Error taking screenshot", e);
        }
    }

    // Highlight Element
    public static void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    // Mouse and Keyboard Actions
    public static void mouseHover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public static void doubleClick(WebElement element) {
        new Actions(driver).doubleClick(element).perform();
    }

    public static void rightClick(WebElement element) {
        new Actions(driver).contextClick(element).perform();
    }
}
