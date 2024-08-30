package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

// this class concerns with any additional function that can helps me (General)
public class Utility {

    private static final String ScreenShoot_Path = "test-outputs/Screenshoots/";

    public static void Clicking_OnElement(WebDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));

        driver.findElement(locator).click();

    }

    public static void SendData(WebDriver driver, By locator, String DataToBeSend) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).sendKeys(DataToBeSend);
    }

    public static String GetText(WebDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return driver.findElement(locator).getText();
    }

    public static WebDriverWait GeneralWait(WebDriver driver) {

        return new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public static WebElement ByToWebElement(WebDriver driver, By locator) {

        return driver.findElement(locator);
    }

    public static ArrayList<WebElement> FindingElementsArrayList(WebDriver driver, By locator) {

        return (ArrayList<WebElement>) driver.findElements(locator);
    }


    public static void ScrollingUsingJS(WebDriver driver, By locator) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollInterview();", ByToWebElement(driver, locator));
    }

    /* For Creating Time Stamp for name of screenshots or email vonrability */
    /* Return the time when the test case run */
    public static String GetTimeStamp() {

        return new SimpleDateFormat("yyyy-MM-dd-hh-mm-ssa").format(new Date());
    }

    public static void TakingScreenShot(WebDriver driver, String ScreenShootName) {

        try {
            File sourcefile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File destinationfile = new File(ScreenShoot_Path + ScreenShootName + "-" + GetTimeStamp() + ".png");

            FileUtils.copyFile(sourcefile, destinationfile);

            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(destinationfile.getPath())));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // take locator for highlighting specific element
    public static void TakingFullScreenShot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(ByToWebElement(driver, locator))
                    .save(ScreenShoot_Path);

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
        }


    }

    public static void SelectingFromDropDown(WebDriver driver, By locator, String option) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        new Select(ByToWebElement(driver, locator)).selectByVisibleText(option);
    }


}
