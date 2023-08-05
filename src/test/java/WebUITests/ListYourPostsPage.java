package WebUITests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ListYourPostsPage {
    private WebDriver driver;
    @FindBy(id = "create-btn") //.//button[@id='create-btn']
    private WebElement createPost;

    @FindBy(xpath = ".//div[@class='pagination svelte-d01pfs']/a[1]")
    private WebElement previousPage;

    @FindBy(linkText = "Next Page")
    private WebElement nextPage;

    @FindBy(xpath = ".//h1")
    public WebElement blogHeader;

    public ListYourPostsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ListYourPostsPage createPost() {
        createPost.click();
        return this;
    }


    public ListYourPostsPage previousPage() {
        previousPage.click();
        return this;
    }

    public ListYourPostsPage nextPage() {
        previousPage.click();
        return this;
    }

    public ListYourPostsPage getHeaderBlog() {
        blogHeader.getText();
        return this;
    }
}
