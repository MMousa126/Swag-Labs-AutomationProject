package PageTesting;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutStepOne {

    private final WebDriver driver;
    private final String firstname = "first-name";
    private final String laatname = "last-name";
    private final String postcode = "postal-code";
    private final String continuebutton = "continue";
    private final String cancelbutton = "cancel";

    private final By continuebuttonid = By.id(continuebutton);
    private final By firstnameid = By.id(firstname);
    private final By lastnameid = By.id(laatname);
    private final By postcodeid = By.id(postcode);
    private final By cancelbuttonid = By.id(cancelbutton);

    public P04_CheckoutStepOne(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutStepOne EnterFirstName(String firstnamedata) {
        Utility.SendData(driver, firstnameid, firstnamedata);
        return this;
    }

    public P04_CheckoutStepOne EnterLastName(String lastnamedata) {
        Utility.SendData(driver, lastnameid, lastnamedata);
        return this;
    }

    public P04_CheckoutStepOne EnterPostCode(String postcodedata) {
        Utility.SendData(driver, postcodeid, postcodedata);
        return this;
    }

    public P05_CheckoutStepTwo ClickOnContinue() {
        Utility.Clicking_OnElement(driver, continuebuttonid);
        return new P05_CheckoutStepTwo(driver);
    }

    public P03_CardPage ClickOnCancel() {

        Utility.Clicking_OnElement(driver, cancelbuttonid);
        return new P03_CardPage(driver);
    }

    public boolean ComparingTheCurrentURLToExpected(String expectedURL) {

        return driver.getCurrentUrl().equals(expectedURL);
    }
}
