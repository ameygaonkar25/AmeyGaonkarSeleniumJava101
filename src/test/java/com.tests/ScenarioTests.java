package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScenarioTests {

    private WebDriver driver;
    private String username = "amey_gaonkar";
    private String accessKey = "30hSMeSDbtOFobcWFuURdD2mRwAwwImqAqbfYfyIlij9KKmhak";
    private String hub = "";
    private String baseURL = "https://www.lambdatest.com/selenium-playground/";

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver (ChromeDriver for this case)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testSimpleFormDemo() {
        // Step 1: Open LambdaTest’s Selenium Playground
        driver.get("https://www.lambdatest.com/selenium-playground");

        // Step 2: Click on “Simple Form Demo”
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement simpleFormDemoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Simple Form Demo")));
        simpleFormDemoLink.click();

        // Step 3: Validate that the URL contains “simple-form-demo”
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

        // Step 4: Create a variable for a string value
        String message = "Welcome to LambdaTest";

        // Step 5: Use this variable to enter values in the “Enter Message” text box
        WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-message")));
        messageBox.sendKeys(message);

        // Step 6: Click the “Get Checked Value” button
        WebElement getCheckedValueButton = driver.findElement(By.id("showInput"));
        getCheckedValueButton.click();

        // Step 7: Validate whether the same text message is displayed in the right-hand panel under the “Your Message:” section
        WebElement displayedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        String displayedText = displayedMessage.getText();
        Assert.assertEquals(displayedText, message, "The message displayed is not as expected");

        System.out.println("Test Passed! The message was correctly displayed.");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
