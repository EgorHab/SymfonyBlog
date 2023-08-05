package WebUITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AuthPage {
    public WebDriver driver;


    @FindBy(xpath = ".//input[@type='text']")
    private WebElement usernameArea;

    @FindBy(xpath = ".//input[@type='password']")
    private WebElement passwordArea;

    @FindBy(xpath = ".//span[@class='mdc-button__label']")
    private WebElement loginButton;

    @FindBy(xpath = ".//div[@class='error-block svelte-uwkxn9']/p[1]")
    private WebElement textError;

    public AuthPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AuthPage fillUserArea(String username) {
        usernameArea.sendKeys(username);
        return this;
    }

    public AuthPage fillPasswordArea(String password) {
        passwordArea.sendKeys(password);
        return this;
    }

    public AuthPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public String getTextError() {
       return textError.getText();

    }




    public void authorization() {
        AuthPage authPage = new AuthPage(driver);


        authPage.fillUserArea("ivanov_ivan");
        authPage.fillPasswordArea("cb0bc330f5");
        authPage.clickLoginButton();
    }


}
