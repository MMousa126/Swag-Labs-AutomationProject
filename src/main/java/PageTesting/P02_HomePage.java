package PageTesting;

import Utilities.LogsUtility;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class P02_HomePage {

    // list because i have so many element i want to click on with the same locator
    public static ArrayList<WebElement> products = new ArrayList<>();
    public static ArrayList<WebElement> selectedproducts = new ArrayList<>();
    private final WebDriver driver;
    private final String numberofproductclass = "shopping_cart_link";
    private final By numberofproduct = By.className(numberofproductclass);
    private final By removefromcardbutton = By.xpath("//button[text() = 'Remove']");
    private String addtocardclass = "//button[@class]";
    private final By addtocardbutton = By.xpath(addtocardclass);

    public P02_HomePage(WebDriver driver) {

        this.driver = driver;
    }

    public By getNumberofproduct() {
        return numberofproduct;
    }

    public P02_HomePage AddAllProductToCard() {

        // to get the size for the products
        products = Utility.FindingElementsArrayList(driver, addtocardbutton);

        for (int i = 1; i <= products.size(); i++) {

            // dynamic locator
            addtocardclass = "(//button[@class])[" + i + "]";
            By dynamicaddtocardbutton = By.xpath(addtocardclass);
            Utility.Clicking_OnElement(driver, dynamicaddtocardbutton);

        }

        return this;
    }

    public String GetTheNumberOfProduct() {
        try {
            System.out.println("Number of Products " + products.size());
            return Utility.GetText(driver, numberofproduct);
        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public Boolean ComparingNumberOfSelectedProductsWithCard() {

        try {
            selectedproducts = Utility.FindingElementsArrayList(driver, removefromcardbutton);
            String numberofselectedproducts = String.valueOf(selectedproducts.size());
            System.out.println("Number of Selected Products " + selectedproducts.size());
            return GetTheNumberOfProduct().equals(numberofselectedproducts);

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
            System.out.println(e.getMessage());
            return false;

        }
    }

}
