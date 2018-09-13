package com.mobile.iehs.pages;

import java.io.IOException;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;


public class BaseClass {

    private static final String FILENAME = "properties.environment";
    public static final String DRIVER_NAME = "webdriver.chrome.driver";
    public static final String DRIVER_PATH = "./driver/chromedriver.exe";

    public static WebDriver driver = null;

    public BaseClass() {

        init();
    }

    public static WebDriver getDriver() {

        return driver;
    }

    @Before
    public void setUp() throws JSONException, UnirestException {
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        // Getting the environment variable
        String env = System.getProperty("com.amazon.test");
        System.out.println("Environment variable is: " + env);

        // If the environment variable is null, then setup with dev variable
        if (env == null) {
            env = "dev";
            System.out.println("Environment is null so it will be changed to: " + env);
        }

        // Getting the URL from the properties file
        ResourceBundle rb = ResourceBundle.getBundle(FILENAME);
        String URL = rb.getString(env);
        driver.get(URL);
        System.out.println("Web browser opened on: " + URL);
    }

    @After
    public void tearDown() throws JSONException, UnirestException {
        driver.quit();
        System.out.println("Web browser closed!");

    }

    private void init() {
        try {
            readProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        //options.addArguments("--disable-geolocation");
        //options.addArguments("headless");
        //options.addArguments("--incognito");

        driver = new ChromeDriver(options);
    }

    private void readProperties() throws IOException {
        System.setProperty(DRIVER_NAME, DRIVER_PATH);
    }



    // Method to find an element and click on it
    public static void findElementAndClick(By selector) {
        waitForElement(selector);
        clickOnElement(selector);
    }


    // Try to click on WebElement By selector
    public static boolean clickOnElement(String selector) {
        int attempts = 0;
        boolean result = false;
        while (attempts < 7) {
            try {
                BaseClass.getDriver().findElement(By.linkText(selector)).click();
                result = true;
                break;

            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            attempts++;
        }

        if (result == false) {
            throw new WebDriverException("The element: " + selector + " couldn't be clicked!");
        }

        return result;
    }

    // Try to click on WebElement By selector
    public static boolean clickOnElement(By selector) {
        int attempts = 0;
        boolean result = false;
        moveToElementBySelector(selector);
        while (attempts < 7) {
            try {
                BaseClass.getDriver().findElement(selector).click();
                result = true;
                break;

            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            attempts++;
        }

        if (result == false) {
            throw new WebDriverException("The element: " + selector + " couldn't be clicked!");
        }

        return result;
    }

    // Try to click on WebElement - Method overload
    public static boolean clickOnElement(WebElement element, By selector) {
        boolean result = false;
        int attempts = 0;
        moveToElement(element);
        while (attempts < 7) {
            try {
                element.findElement(selector).click();
                result = true;
                break;
            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            attempts++;
        }

        if (result == false) {
            throw new WebDriverException("The element: " + selector + " couldn't be clicked!");
        }

        return result;
    }

    // Try to click on WebElement - Method overload
    public static boolean clickOnElement(WebElement element) {
        boolean result = false;
        int attempts = 0;
        moveToElement(element);


        while (attempts < 7) {
            try {
                element.click();
                System.out.println("Clicked on the element from the grid");
                result = true;
                break;

            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
            attempts++;
        }

        if (result == false) {
            throw new WebDriverException("The element: " + element + " couldn't be clicked!");
        }

        return result;
    }

    // Try to click on WebElement By selector
    public static boolean clickOnElement(By areaSelector, By selector) {
        int attempts = 0;
        boolean result = false;

        while (attempts < 7) {
            try {
                BaseClass.getDriver().findElement(areaSelector).findElement(selector).click();
                result = true;
                break;

            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            attempts++;
        }

        if (result == false) {
            throw new WebDriverException("The element: " + selector + " couldn't be clicked!");
        }

        return result;
    }

    // -------------- DRIVER METHODS - WAIT - ELEMENT -------------- //

    By seleniumSelector(String selector) {
        return null;
    }

    // Method to wait for a specific element to be clickable
    public static void waitForElement(By seleniumSelector) {
/*
        FluentWait wait = new FluentWait(BaseClass.getDriver())
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOfElementLocated(seleniumSelector));
        wait.until(ExpectedConditions.elementToBeClickable(seleniumSelector));
*/

        WebDriverWait driverWait = new WebDriverWait(BaseClass.getDriver(), 15);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(seleniumSelector));
        driverWait.until(ExpectedConditions.elementToBeClickable(seleniumSelector));
        driverWait.ignoring(NoSuchElementException.class);
        driverWait.ignoring(StaleElementReferenceException.class);

    }

    // Method to wait for an element on area
    public static void waitForElementOnArea(By areaSelector, By seleniumSelector) {

        WebDriverWait driverWait = new WebDriverWait(BaseClass.getDriver(), 15);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(areaSelector)).findElement(seleniumSelector);
        driverWait.until(ExpectedConditions.elementToBeClickable(areaSelector)).findElement(seleniumSelector);
        driverWait.ignoring(NoSuchElementException.class);
        driverWait.ignoring(StaleElementReferenceException.class);
    }

    // Method to wait for a text on area
    public static void waitForTextOnArea(By areaSelector, String text) {

        WebDriverWait driverWait = new WebDriverWait(BaseClass.getDriver(), 15);
        driverWait.until(ExpectedConditions.textToBePresentInElementLocated(areaSelector, text));
        driverWait.until(ExpectedConditions.elementToBeClickable(areaSelector));
        driverWait.ignoring(NoSuchElementException.class);
        driverWait.ignoring(StaleElementReferenceException.class);
    }

    // Method to find an element and send a text
    public static void findElementAndSendKey(By selector, String sendKey) {

        waitForElement(selector);
        int attempts = 0;
        while (attempts < 7) {
            try {
                BaseClass.getDriver().findElement(selector).clear();
                BaseClass.getDriver().findElement(selector).sendKeys(sendKey);
                break;

            } catch (WebDriverException e) {
                System.out.println("~~~~~~ EXCEPTION CAUGHT: WebDriverException ~~~~~~");
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            attempts++;
        }

    }


    // -------------- DRIVER METHODS - MOVE -------------- //

    // Move to an Element by JavaScript by a selector
    public static void moveToElementBySelector(By selector) {
        waitForElement(selector);
        WebElement element = BaseClass.getDriver().findElement(selector);
        ((JavascriptExecutor) BaseClass.getDriver()).executeScript("arguments[0].scrollIntoView()", element);
    }

    // Move to an Element by JavaScript
    public static void moveToElement(WebElement element) {
        ((JavascriptExecutor) BaseClass.getDriver()).executeScript("arguments[0].scrollIntoView()", element);
    }

    // Method to scroll down by JavaScript
    public static void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
    }

    // -------------- DRIVER METHODS - GET TEXT -------------- //

    // Method to GET text from area
    public static String getTextFromArea(By areaSelector) {
        WebElement divElement = BaseClass.getDriver().findElement(areaSelector);
        String areaText = divElement.getText();
        System.out.println("The Area Text is: " + areaText);
        return areaText;
    }

    // Method to GET text from attribute value from area
    public static String getAttributeValueTextFromArea(By areaSelector) {
        String valueInserted = BaseClass.getDriver().findElement(areaSelector).getAttribute("value");
        System.out.println("The Attribute Value Text is: " + valueInserted);
        return valueInserted;
    }

    // Method to GET text from attribute placeholder from area
    public static String getAttributePlaceholderTextFromArea(By areaSelector) {
        String placeholderText = BaseClass.getDriver().findElement(areaSelector).getAttribute("placeholder");
        System.out.println("The Placeholder Text is: " + placeholderText);
        return placeholderText;
    }


}
