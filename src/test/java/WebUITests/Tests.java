package WebUITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Tests {

    public String username = "ivanov_ivan";
    public String password = "cb0bc330f5";
    public String title = "test 16:49";
    public String description = "test 16:49";
    public String content = "test 16:49";
    static WebDriver driver;

    @AfterAll
    static void cleanUp() {
        driver.quit();
    }

    @Test
    @Description("positive test case with valid username/password")
    void successAuthTest() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);


        AuthPage authPage = new AuthPage(driver);
        ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);


        driver.get("https://test-stand.gb.ru/login");


        authPage.fillUserArea(username);
        authPage.fillPasswordArea(password);
        authPage.clickLoginButton();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h1")));

        Assertions.assertEquals("https://test-stand.gb.ru/", driver.getCurrentUrl());

        //System.out.println(driver.getCurrentUrl());
    }

    @Test
    @Description("negative test case with invalid username (empty field)")
    void failedAuthTest1() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        AuthPage authPage = new AuthPage(driver);
        ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get("https://test-stand.gb.ru/login");


        authPage.fillUserArea("");
        authPage.fillPasswordArea(password);
        authPage.clickLoginButton();

        Assertions.assertEquals("https://test-stand.gb.ru/login", driver.getCurrentUrl());
        //System.out.println(authPage.getTextError());
        Assertions.assertEquals("Поле не может быть пустым", authPage.getTextError());

    }

    @Test
    @Description("negative test case with invalid username(username less than 3 symbol)")
    void failedAuthTest2() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        AuthPage authPage = new AuthPage(driver);

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        //WebDriverWait wait = new WebDriverWait(driver, 10);


        driver.get("https://test-stand.gb.ru/login");
        authPage.fillUserArea("Mz");
        authPage.fillPasswordArea("0a5f476630");
        authPage.clickLoginButton();

        Assertions.assertEquals("https://test-stand.gb.ru/login", driver.getCurrentUrl());
        //System.out.println(authPage.getTextError());
        Assertions.assertEquals("Неправильный логин. Может быть не менее 3 и не более 20 символов", authPage.getTextError());
    }

    @Test
    @Description("negative test case with invalid username(username more than 20 symbol)")
    void failedAuthTest3() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        AuthPage authPage = new AuthPage(driver);
        //ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebDriverWait wait = new WebDriverWait(driver, 10);


        driver.get("https://test-stand.gb.ru/login");

        authPage.fillUserArea("thisLoginHasMoreThanTwentySymbol");
        authPage.fillPasswordArea("a7daf8f747");
        authPage.clickLoginButton();

        Assertions.assertEquals("https://test-stand.gb.ru/login", driver.getCurrentUrl());
        //System.out.println(authPage.getTextError());
        Assertions.assertEquals("Неправильный логин. Может быть не менее 3 и не более 20 символов", authPage.getTextError());
    }

    @Test
    @Description("positive test case with valid username/password (corner)")
    void successAuthTestCorner3() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        AuthPage authPage = new AuthPage(driver);
        //ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);


        driver.get("https://test-stand.gb.ru/login");

        authPage.fillUserArea("zyx");
        authPage.fillPasswordArea("fac97e5796");
        authPage.clickLoginButton();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h1")));

        Assertions.assertEquals("https://test-stand.gb.ru/", driver.getCurrentUrl());
    }


    @Test
    @Description("positive test case with valid username/password (corner)")
    void successAuthTestCorner20() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();


        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        AuthPage authPage = new AuthPage(driver);
        //ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get("https://test-stand.gb.ru/login");


        driver.findElement(By.xpath(".//input[@type='text']")).click();
        Thread.sleep(2000);
        authPage.fillUserArea("thisLoginHas20Symbbb");
        authPage.fillPasswordArea("6245cd5595");
        authPage.clickLoginButton();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h1")));

        Assertions.assertEquals("https://test-stand.gb.ru/", driver.getCurrentUrl());
    }

    @Test
    @Description("positive test create new post")
    void createNewPost() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        options.addArguments("--incognito");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        driver.get("https://test-stand.gb.ru/login");

        AuthPage authPage = new AuthPage(driver);
        ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);
        CreatePostPage createPostPage = new CreatePostPage(driver);
        InsideYourPost insideYourPost = new InsideYourPost(driver);


        authPage.authorization();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//div[@class='button-block svelte-1e9zcmy']")));

        listYourPostsPage.createPost();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//input[@type='text']")));
        createPostPage.clickTitleArea();

        Thread.sleep(2000);

        createPostPage.fillTitleArea(title);

        Thread.sleep(2000);

        createPostPage.fillDescriptionArea(description);
        createPostPage.fillContentArea(content);
        createPostPage.save();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[@class='content svelte-tv8alb']")));
        Assertions.assertEquals("test 16:49", insideYourPost.getContent());
        Assertions.assertEquals("test 16:49", insideYourPost.getTitlePost());
        Assertions.assertTrue(driver.findElements(By.xpath(".//img[@class='svelte-tv8alb']")).size() > 0, "картинка отсутствует");


        insideYourPost.delete();
    }


    @Test
    @Description("Moving between pages test")
    void movingBetweenPages() throws InterruptedException {
        WebDriverManager.chromedriver().setup();


        ChromeOptions options = new ChromeOptions();

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        options.addArguments("--incognito");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://test-stand.gb.ru/login");

        AuthPage authPage = new AuthPage(driver);
        ListYourPostsPage listYourPostsPage = new ListYourPostsPage(driver);

        authPage.authorization();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");


        Thread.sleep(2000);

        listYourPostsPage.nextPage();

        Thread.sleep(1000);

        listYourPostsPage.previousPage();

        Assertions.assertTrue(driver.findElements(By.xpath(".//div[@class='pagination svelte-d01pfs']/a[2]")).size() > 0);


    }


}
