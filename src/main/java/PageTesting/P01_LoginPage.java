package PageTesting;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class P01_LoginPage {

    private final WebDriver driver;
    String id_username = "user-name";
    private final By username = By.id(id_username);
    String id_password = "password";
    private final By password = By.id(id_password);
    String id_login = "login-button";
    private final By loginbutton = By.id(id_login);
    String class_logo = "secret_sauce";
    private final By logo = By.className(class_logo);


    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public P01_LoginPage EnterUserName(String usernametext) {

        Utility.SendData(driver, username, usernametext);
        return this;
    }

    public P01_LoginPage EnterPassword(String passwordtext) {

        Utility.SendData(driver, password, passwordtext);
        return this;
    }

    public P02_HomePage ClickOnLogin() {

        Utility.Clicking_OnElement(driver, loginbutton);
        return new P02_HomePage(driver);

    }

    public boolean AssertLoginTC(String expected) {

        return driver.getCurrentUrl().equals(expected);


    }

}
