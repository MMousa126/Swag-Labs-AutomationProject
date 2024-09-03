package PageTesting;

import Utilities.LogsUtility;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CardPage {
    static float TotalPrices = 0;
    private final WebDriver driver;
    private final String noofselectedproducts = "(//button[text() = 'Remove']//preceding-sibling::div[@class = 'inventory_item_price'])";
    private final By noofselectedproductslocator = By.xpath(noofselectedproducts);
    private final String checkbox = "checkout";
    private final By checkboxbuttonid = By.id(checkbox);


    public P03_CardPage(WebDriver driver) {
        this.driver = driver;
    }


//    public boolean ComparePricesFromHomePageAndCard() {
////
////        if (GetThePricesOfSelectedproductFromCardPage() == new P02_HomePage(driver).GetThePricesOfSelectedProductInHomePag()){
////
////            return true;
////        }
////        else{
////            return false;
////        }
//        return GetThePricesOfSelectedproductFromCardPage().equals(new P02_HomePage(driver).GetThePricesOfSelectedProductInHomePag());
//    }

    public String GetThePricesOfSelectedproductFromCardPage() {

        List<WebElement> selectedproductelements = Utility.FindingElementsArrayList(driver, noofselectedproductslocator);
        By individualpricelocator;

        for (int i = 1; i <= selectedproductelements.size(); i++) {

            individualpricelocator = By.xpath("(//button[text() = 'Remove']//preceding-sibling::div[@class = 'inventory_item_price'])[" + i + "]");
            TotalPrices += Float.parseFloat(Utility.GetText(driver, individualpricelocator).replace("$", ""));
        }
        LogsUtility.LoggerInfo("the total price in Card Page is " + TotalPrices);
        return String.valueOf(TotalPrices);
    }

    public boolean ComparingPrices(String price) {
        return GetThePricesOfSelectedproductFromCardPage().equals(price);
    }

    public P04_CheckoutStepOne ClickOnCheckOutButton() {

        Utility.Clicking_OnElement(driver, checkboxbuttonid);
        return new P04_CheckoutStepOne(driver);

    }
}
