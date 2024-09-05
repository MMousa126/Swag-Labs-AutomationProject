package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

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

    //TODO: Checking Broken Link and Broken Image Using HTTP Connection
    /*
     * @elements -> the elements that have link or image
     * @Image or Link -> choose the type of checking
     * This Function Throws 2 Exceptions MalformedURLException and URISyntaxException
     * This Function Using HTTP Connection
     * */
    public static void CheckBrokenLinkAndImageUsingHTTPConnection(List<WebElement> elements, String typeofcheck) {
        URL url = null;
        String type = typeofcheck.toLowerCase();
        String attribute = null;
        HttpURLConnection httpURLConnection = null;
        if (type.equals("image")) {
            attribute = "src";
        } else if (type.equals("link")) {
            attribute = "href";
        } else {
            LogsUtility.LoggerError("Error Type.\n" +
                    "The Types Image or Link");
        }
        for (WebElement element : elements) {
            try {
                url = new URI(element.getAttribute(attribute)).toURL();
                httpURLConnection = (HttpURLConnection) url.openConnection();
                LogsUtility.LoggerInfo(httpURLConnection.getResponseMessage() + " " + httpURLConnection.getResponseCode());
            } catch (URISyntaxException | IOException e) {
                LogsUtility.LoggerError("Exception");
                e.printStackTrace();
            }

        }
    }

    //TODO: Checking Broken Link and Broken Image Using RestAssured
    /*
     * @elements -> the elements that have link or image
     * @Image or Link -> choose the type of checking
     * This Function Throws 2 Exceptions MalformedURLException and URISyntaxException
     * This Function Using RestAssured
     * */
    public static void CheckBrokenLinkAndImageUsingRestAssured(List<WebElement> elements, String typeofcheck) {
        List<URL> url = new ArrayList<>();
        String type = typeofcheck.toLowerCase();
        String attribute = null;
        if (type.equals("image")) {
            attribute = "src";
        } else if (type.equals("link")) {
            attribute = "href";
        } else {
            LogsUtility.LoggerError("Error Type.\n" +
                    "The Types Image or Link");
        }
        for (WebElement element : elements) {
            try {
                url.add(new URI(element.getAttribute(attribute)).toURL());
            } catch (MalformedURLException | URISyntaxException e) {
                LogsUtility.LoggerError("Exception");
                e.printStackTrace();
            }
        }
        for (URL elementurl : url) {
            Response response = RestAssured.given().get(elementurl);
            LogsUtility.LoggerInfo(response.getStatusLine());
        }
    }

    // why +1 because the locator start with 1 not like the arrays
    // this function return random number between 1 and the upper number
    private static int GenerateRandomNumber(int upper) {
        return (new Random().nextInt(upper)) + 1;
    }

    // why set because set have only unique numbers
    // this function could throw an infinite loop if upper is less than  noofproduct
    // the upper should be more than the no of the products
    public static Set<Integer> GenerateUniqueRandomNumbers(int upper, int noofproduct) {

        Set<Integer> random = new HashSet<>();


        if (upper <= noofproduct) {
            LogsUtility.LoggerError("the Upper number is less than the number of the product");
            throw new IllegalArgumentException("The number of products should be smaller than the upper bound.");
        } else {
            while (random.size() < noofproduct) {
                random.add(GenerateRandomNumber(upper));
            }
            return random;

        }
    }

    public static boolean VerifyCurrentURLToExpected(WebDriver driver, String expectedURL) {

        try {
            GeneralWait(driver).until(ExpectedConditions.urlToBe(expectedURL));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean ComparingTheCurrentURLToExpected(WebDriver driver, String expectedURL) {

        return driver.getCurrentUrl().equals(expectedURL);
    }

    // for regression test for storing only one latest file from logs
    public static File GetLatestFile(String folderpath) {
        File folder = new File(folderpath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        return files[0];
    }

    // we want to make a session for login instead of every time i have to login i will login inside before test
    // so i don't need to login every time using cookies

    /* Take all Cookies from The Website */
    public static Set<Cookie> GetAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    /* Injecting all the cookies to the driver */
    public static void InjectCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }


    /* Like injecting Registration for the preconditions */
    public static String InjectRequestUsingPostAPI(String postrequest_url, String contantrequesttype, String bodytobeposted) {

        return RestAssured
                .given()
                .contentType(contantrequesttype)
                .body(bodytobeposted)
                .when()
                .post(postrequest_url)
                .then()
                .extract()
                .body()
                .asString();
    }
}
