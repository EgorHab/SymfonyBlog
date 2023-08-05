package WebUITests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InsideYourPost {
    private WebDriver driver;
    @FindBy(xpath = "//button[2]")
    private WebElement deleteButton;

    @FindBy(xpath = ".//div[@class='button-block svelte-tv8alb'] //button[1]")
    private WebElement updateButton;

    @FindBy(xpath = ".//div[@class='item-image']") //.//img[@class='svelte-tv8alb']
    private WebElement imagePost;

    @FindBy(xpath = ".//div[@class='content svelte-tv8alb']")
    private WebElement contentPost;

    @FindBy(xpath = ".//h1[@class='svelte-tv8alb']")
    private WebElement titlePost;

    public InsideYourPost(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public InsideYourPost delete() {
        deleteButton.click();
        return this;
    }

    public InsideYourPost update() {
        updateButton.click();
        return this;
    }

    public String getImage() {
        return imagePost.getText();

    }

    public String getContent() {
        return contentPost.getText();
    }

    public String getTitlePost() {
        return titlePost.getText();
    }


}
