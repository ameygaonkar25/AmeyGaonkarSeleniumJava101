package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ScenarioTests {

    private WebDriver driver;
    private String username = "amey_gaonkar";
    private String accessKey = "30hSMeSDbtOFobcWFuURdD2mRwAwwImqAqbfYfyIlij9KKmhak";
    private String hub = "@hub.lambdatest.com/wd/hub";
    private String baseURL = "https://www.lambdatest.com/selenium-playground/";

    DesiredCapabilities capabilities = new DesiredCapabilities();

    @Parameters(value={"Browser","Version","Platform"})
    @BeforeMethod
    public void setUp(String browser, String version, String platform) {
        capabilities.setCapability("build", "1.7");
        capabilities.setCapability("name", "Cross Browser Testing");
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform);
        capabilities.setCapability("network", true);
        capabilities.setCapability("console", "true");
        capabilities.setCapability("visual", true);
        capabilities.setCapability("video", true);

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), capabilities);
            driver.manage().window().maximize();
            driver.get(baseURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.get(baseURL);
    }

    @Test (timeOut = 20000)
    public void testSimpleFormDemo() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement simpleFormDemoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Simple Form Demo")));
        simpleFormDemoLink.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

        String message = "Welcome to LambdaTest";

        WebElement messageBox= driver.findElement(By.xpath("(//*[@id='user-message'])[1]"));
        messageBox.sendKeys(message);
        driver.findElement(By.id("showInput")).click();

        WebElement displayedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        String actualMessage = displayedMessage.getText();
        Assert.assertEquals(actualMessage, message, "The message displayed is not as expected");

        System.out.println("Test Passed! The message was correctly displayed.");
    }

    @Test (timeOut = 20000)
    public void testDragAndDropSlider() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dragDropSlidersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Drag & Drop Sliders")));
        dragDropSlidersLink.click();

        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='slider3']/div/input")));

        for (int i = 1; i <= 80 ; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        String actualText = driver.findElement(By.id("rangeSuccess")).getText();
        Assert.assertEquals(actualText,"95");
    }

    @Test (timeOut = 20000)
    public void testInputFormSubmit() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement inputFormSubmitLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Input Form Submit")));
        inputFormSubmitLink.click();

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("inputEmail4"));
        Assert.assertTrue(nameField.getAttribute("validationMessage").contains("Please fill out this field."));
        Assert.assertTrue(emailField.getAttribute("validationMessage").contains("Please fill out this field."));

        nameField.sendKeys("Amey");
        WebElement emailField2 = driver.findElement(By.id("inputEmail4"));
        emailField2.sendKeys("abcd@xyz.com");
        driver.findElement(By.id("inputPassword4")).sendKeys("password123");
        driver.findElement(By.id("company")).sendKeys("ABC Company");
        driver.findElement(By.id("websitename")).sendKeys("www.abc.com");
        driver.findElement(By.id("inputCity")).sendKeys("Verna");
        driver.findElement(By.id("inputAddress1")).sendKeys("123 ");
        driver.findElement(By.id("inputAddress2")).sendKeys("101");
        driver.findElement(By.id("inputState")).sendKeys("GOA");
        driver.findElement(By.id("inputZip")).sendKeys("401101");

        WebElement countryDropdown = driver.findElement(By.id("inputCountry"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("United States");

        submitButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
        String successText = successMessage.getText();
        System.out.println(successText);
        Assert.assertEquals(successText, "Thanks for contacting us, we will get back to you shortly.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}