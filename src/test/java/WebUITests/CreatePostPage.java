package WebUITests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreatePostPage {
    private WebDriver driver;

    @FindBy(xpath = ".//input[@type='text']")
    private WebElement titleArea;

    @FindBy(xpath = "//textarea[@maxlength='100']")
    private WebElement descriptionArea;

    @FindBy(xpath = "//textarea[@aria-controls='SMUI-textfield-helper-text-1']")
    private WebElement contentArea;

    @FindBy(xpath = "//span[@class='mdc-button__label']")
    private WebElement saveButton;

    public CreatePostPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public CreatePostPage clickTitleArea () {
        titleArea.click();
        return this;
    }

    public CreatePostPage fillTitleArea(String title) {
        titleArea.sendKeys(title);
        return this;
    }

    public CreatePostPage fillDescriptionArea(String description) {
        descriptionArea.sendKeys(description);
        return this;
    }

    public CreatePostPage fillContentArea(String content) {
        contentArea.sendKeys(content);
        return this;
    }

    public CreatePostPage save() {
        saveButton.click();
        return this;
    }
}
