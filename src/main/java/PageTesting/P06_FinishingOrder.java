package PageTesting;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P06_FinishingOrder {
    final String visabletext = "//h2[text() = 'Thank you for your order!']";
    final By visabilityoftextxpath = By.xpath(visabletext);


    final String gohome = "back-to-products";
    final By gohomebuttonid = By.id(gohome);


    private final WebDriver driver;


    public P06_FinishingOrder(WebDriver driver) {
        this.driver = driver;
    }

    public boolean VisibilityOfTheText() {
        try {
            Utility.GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(visabilityoftextxpath));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public P02_HomePage ClickOnGoHome() {
        Utility.Clicking_OnElement(driver, gohomebuttonid);
        return new P02_HomePage(driver);
    }

}
