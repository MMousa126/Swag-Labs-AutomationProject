package PageTesting;

import Utilities.LogsUtility;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class P02_HomePage {

    // list because i have so many element i want to click on with the same locator
    public static ArrayList<WebElement> products = new ArrayList<>();
    public static ArrayList<WebElement> selectedproducts = new ArrayList<>();
    static float TotalPrices = 0;
    private static WebDriver driver;
    private final String numberofproductclass = "shopping_cart_link";
    private final String noofselectedproducts = "(//button[text() = 'Remove']//preceding-sibling::div[@class = 'inventory_item_price'])";
    private final String cardicon = "shopping_cart_link";
    private final By noofselectedproductslocator = By.xpath(noofselectedproducts);
    private final By cardiconlocator = By.className(cardicon);
    private final By numberofproduct = By.className(numberofproductclass);
    private final By removefromcardbutton = By.xpath("//button[text() = 'Remove']");
    private final String threedash = "react-burger-menu-btn";
    private final String aboutus = "[data-test = 'about-sidebar-link']";
    private final String logout = "logout_sidebar_link";
    private final By threedashid = By.id(threedash);
    private final By logoutid = By.id(logout);
    private final By aboutuscss = By.cssSelector(aboutus);
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

    public P02_HomePage AddRandomProducttoCard(int upper, int noofproduct) {

        Set<Integer> randomnumbers = Utility.GenerateUniqueRandomNumbers(upper, noofproduct);

        for (Integer rand : randomnumbers) {
            LogsUtility.LoggerInfo("the Random Item is " + rand);
            By dynamicaddtocardbutton = By.xpath("(//button[@class])[" + rand + "]");
            Utility.Clicking_OnElement(driver, dynamicaddtocardbutton);

        }
        return this;
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

    public P03_CardPage ClickOnCardIcon() {

        Utility.Clicking_OnElement(driver, cardiconlocator);
        return new P03_CardPage(driver);

    }

    public boolean ComparingTheCurrentURLToExpected(String expectedURL) {

        return driver.getCurrentUrl().equals(expectedURL);
        //or
//        try {
//            Utility.GeneralWait(driver).until(ExpectedConditions.urlToBe(expectedURL));

//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
        // or
//        if (Utility.GeneralWait(driver).until(ExpectedConditions.urlToBe(expectedURL)))
//        {
//            return true;
//        }else{
//            return false;
//        }

    }

    public String GetThePricesOfSelectedProductInHomePag() {

        List<WebElement> selectedproductelements = Utility.FindingElementsArrayList(driver, noofselectedproductslocator);
        By individualpricelocator;
        for (int i = 1; i <= selectedproductelements.size(); i++) {

            individualpricelocator = By.xpath("(//button[text() = 'Remove']//preceding-sibling::div[@class = 'inventory_item_price'])[" + i + "]");
            TotalPrices += Float.parseFloat(Utility.GetText(driver, individualpricelocator).replace("$", ""));
        }
        LogsUtility.LoggerInfo("the prices in the Home page is " + TotalPrices);
        return String.valueOf(TotalPrices);
    }

    public P02_HomePage ThreeDashesPage() {

        Utility.Clicking_OnElement(driver, threedashid);
        return this;
    }

    public void ClickOnAboutUsPage() {
        Utility.Clicking_OnElement(driver, aboutuscss);
    }

    public void LoggingOut() {
        Utility.Clicking_OnElement(driver, logoutid);
    }

}
