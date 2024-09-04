package Tests;

import Factory.DriverFactory;
import Listeners.IInvokedMethodListeners;
import Listeners.ITestResultListeners;
import PageTesting.P01_LoginPage;
import PageTesting.P05_CheckoutStepTwo;
import Utilities.DataUtility;
import Utilities.LogsUtility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static Factory.DriverFactory.GetThreadDriver;
import static Factory.DriverFactory.SetupThreadDriver;

@Listeners({IInvokedMethodListeners.class, ITestResultListeners.class})
public class TC05_FinishShopping {
    private final String Browser_FileName = "environment";
    private final String Browser_Key = "Browser";
    private final String Base_URLKey = "Base_URL";
    private final String DataJsonFileName = "ValidLoginTestData";
    private final String usernamefield = "username";
    private final String passwordfield = "password";
    private final String homepage = "Home_URL";
    private final String cardpage = "Card_URL";

    private final String checkoutfile = "CheckOutData";
    private final String firstname = "firstname";
    private final String lastname = "lastname";
    private final String postcode = "postcode";
    /* Environment variables from properties */
    private final String BROWSER = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Browser_Key);
    private final String URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Base_URLKey);
    private final String HOME_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, homepage);
    private final String Card_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, cardpage);
    /* This Attributes for sending the username and password field using json file */
    private final String USERNAME = DataUtility.GetJsonDataFromFile(DataJsonFileName, usernamefield);
    private final String PASSWORD = DataUtility.GetJsonDataFromFile(DataJsonFileName, passwordfield);
    private final String FirstName = DataUtility.GetJsonDataFromFile(checkoutfile, firstname);
    private final String LastName = DataUtility.GetJsonDataFromFile(checkoutfile, lastname);
    private final String PostCode = DataUtility.GetJsonDataFromFile(checkoutfile, postcode);


    private final String checkoutone = "CheckOutStepOne_URL";
    private final String checkouttwo = "CheckOutStepTwo_URL";
    private final String CheckOutURLStepOne = DataUtility.GetPropertiesDataFromFile(Browser_FileName, checkoutone);
    private final String CheckOutURLStepTwo = DataUtility.GetPropertiesDataFromFile(Browser_FileName, checkouttwo);

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
    public void CheckingThePricesOfWebsiteToTheCollected() {

        String price = new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .AddRandomProducttoCard(5, 3)
                .ClickOnCardIcon()
                .ClickOnCheckOutButton()
                .EnterFirstName(FirstName)
                .EnterLastName(LastName)
                .EnterPostCode(PostCode)
                .ClickOnContinue()
                .TotalPriceWithoutTaxFromItems();

        LogsUtility.LoggerInfo("the price is " + price);

        softAssert.assertTrue(new P05_CheckoutStepTwo(GetThreadDriver()).CompareItemPricesToWebPrices(price));
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void CheckingCalculationOfTaxesIsRight() {

        float price = new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .AddRandomProducttoCard(5, 3)
                .ClickOnCardIcon()
                .ClickOnCheckOutButton()
                .EnterFirstName(FirstName)
                .EnterLastName(LastName)
                .EnterPostCode(PostCode)
                .ClickOnContinue()
                .GetTheTotalTax();

        LogsUtility.LoggerInfo("the taxes calculation is " + price);

        softAssert.assertTrue(new P05_CheckoutStepTwo(GetThreadDriver()).CheckCalculationOfTaxes(price));
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void CheckingCalculationOfTotalPriceIsRight() {

        String price = new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .AddRandomProducttoCard(5, 3)
                .ClickOnCardIcon()
                .ClickOnCheckOutButton()
                .EnterFirstName(FirstName)
                .EnterLastName(LastName)
                .EnterPostCode(PostCode)
                .ClickOnContinue()
                .GetTheTotalPriceWithTax();

        LogsUtility.LoggerInfo("the taxes calculation is " + price);

        softAssert.assertTrue(new P05_CheckoutStepTwo(GetThreadDriver()).ComparingTheTotalToTheWebSite(price));
        softAssert.assertAll();
    }

    @AfterMethod
    public void Quit() {

        DriverFactory.QuitThreadDriver();

    }
}
