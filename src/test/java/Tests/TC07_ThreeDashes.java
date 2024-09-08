package Tests;

import Factory.DriverFactory;
import PageTesting.P01_LoginPage;
import Utilities.DataUtility;
import Utilities.LogsUtility;
import Utilities.Utility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static Factory.DriverFactory.GetThreadDriver;
import static Factory.DriverFactory.SetupThreadDriver;

public class TC07_ThreeDashes {
    private final String Browser_FileName = "environment";
    private final String Browser_Key = "Browser";
    private final String Base_URLKey = "Base_URL";
    private final String DataJsonFileName = "ValidLoginTestData";
    private final String usernamefield = "username";
    private final String passwordfield = "password";
    private final String homepage = "Home_URL";
    private final String cardpage = "Card_URL";
    /* Environment variables from properties */
    private final String BROWSER = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Browser_Key);
    private final String URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Base_URLKey);
    private final String HOME_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, homepage);
    private final String Card_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, cardpage);
    /* This Attributes for sending the username and password field using json file */
    private final String USERNAME = DataUtility.GetJsonDataFromFile(DataJsonFileName, usernamefield);
    private final String PASSWORD = DataUtility.GetJsonDataFromFile(DataJsonFileName, passwordfield);

    private final String AboutUs = "AboutUs_URL";
    private final String AboutUs_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, AboutUs);


    private final SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void SetUp() {

        try {

            SetupThreadDriver(BROWSER);

            LogsUtility.LoggerInfo("Edge is Opened Correctly");
            GetThreadDriver().get(URL);
            LogsUtility.LoggerInfo("Page is Redirected to the URL");

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Test(priority = 1)
    public void AboutUs() {

        new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .ThreeDashesPage()
                .ClickOnAboutUsPage();

        softAssert.assertTrue(Utility.VerifyCurrentURLToExpected(GetThreadDriver(), AboutUs_URL));
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void ClickOnLogOut() {

        new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .ThreeDashesPage()
                .LoggingOut();

        softAssert.assertTrue(Utility.VerifyCurrentURLToExpected(GetThreadDriver(), URL));
        softAssert.assertAll();
    }

    @AfterMethod
    public void Quit() {

        DriverFactory.QuitThreadDriver();

    }
}
