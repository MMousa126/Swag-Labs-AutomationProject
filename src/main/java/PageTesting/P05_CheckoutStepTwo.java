package PageTesting;

import Utilities.LogsUtility;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P05_CheckoutStepTwo {

    static float totalPrices = 0;
    private final String itemtotal = "[data-test = subtotal-label]";
    private final By totalitemscss = By.cssSelector(itemtotal);

    private final String tax = "[data-test = tax-label]";
    private final By taxcss = By.cssSelector(tax);

    private final String totalwithtax = "[data-test=total-label]";
    private final By totalwithtaxcss = By.cssSelector(totalwithtax);

    private final String finish = "finish";
    private final By finishbutton = By.id(finish);

    private final String cancel = "cancel";
    private final By cancelbutton = By.id(cancel);

    private final String paying_items = "(//div[@data-test='inventory-item-price'])";
    private final By paying_items_csslocator = By.xpath(paying_items);


    private final WebDriver driver;

    public P05_CheckoutStepTwo(WebDriver driver) {
        this.driver = driver;
    }

    public String TotalPriceWithoutTaxFromItems() {
        List<WebElement> csslocatorsofproducts = Utility.FindingElementsArrayList(driver, paying_items_csslocator);
        By individualitem;
        String individualtextprice;

        for (int i = 1; i <= csslocatorsofproducts.size(); i++) {
            individualitem = By.xpath("(//div[@data-test='inventory-item-price'])[" + i + "]");
            individualtextprice = Utility.GetText(driver, individualitem);
            totalPrices += Float.parseFloat(individualtextprice.replace("$", ""));
        }
        return String.valueOf(totalPrices);
    }


    public float GetTheTotalPriceWithoutTax() {

        return Float.parseFloat(Utility.GetText(driver, totalitemscss).replace("Item total: $", ""));
    }

    public boolean CompareItemPricesToWebPrices(String expected) {

        if (expected.equals(String.valueOf(GetTheTotalPriceWithoutTax()))) {
            return true;
        }
        return false;
    }

    public float GetTheTotalTax() {

        return Float.parseFloat(Utility.GetText(driver, taxcss).replace("Tax: $", ""));
    }

    public String CollectingTheTotalTaxesAndItems() {

        return String.valueOf(GetTheTotalPriceWithoutTax() + GetTheTotalTax());
    }

    public String GetTheTotalPriceWithTax() {

        return String.valueOf(Float.parseFloat(Utility.GetText(driver, totalwithtaxcss).replace("Total: $", "")));
    }

    public boolean ComparingTheTotalToTheWebSite(String theexpectedtotal) {

        if (theexpectedtotal.equals(CollectingTheTotalTaxesAndItems())) {
            LogsUtility.LoggerInfo("the GetTheTotalPriceWithTax is " + theexpectedtotal);
            LogsUtility.LoggerInfo("the CollectingTheTotalTaxesAndItems is " + CollectingTheTotalTaxesAndItems());

            return true;
        }
        return false;
    }

    public float CalculateTaxes() {
        float total = Float.valueOf(TotalPriceWithoutTaxFromItems());
        LogsUtility.LoggerInfo("the total in the function is " + total);
        float calctax = (float) ((total * 8.0) / 100.0);
        LogsUtility.LoggerInfo("the calctax in the function is " + calctax);
        float tax = (float) (Math.round(calctax * 100.0) / 100.0);

        return tax;
    }

    public boolean CheckCalculationOfTaxes(float taxes) {
        if (taxes == CalculateTaxes())
            return true;

        return false;
    }


}
