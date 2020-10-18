package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message)
    {
        return waitForElementPresent(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public WebElement assertElementHasText(String locator, String expected_text, String error_message){
        WebElement element = waitForElementPresent(locator, "Element " + locator + " not found");
        String actual_text = element.getText();
        Assert.assertEquals(error_message, expected_text, actual_text);
        return element;
    }

    public WebElement assertElementContainsText(String locator, String expected_text, String error_message){
        WebElement element = waitForElementPresent(locator, "Element " + locator + " not found");
        String actual_text = element.getText();
        boolean is_contains = actual_text.contains(expected_text);
        Assert.assertTrue(error_message, is_contains);
        return element;
    }

    public List<WebElement> assertElementsContainText(String locator, String expected_text, String error_message){
        List <WebElement> webElements = waitAndGetWebElements(locator, "Element by " + locator + " not found");
        String actual_text;
        boolean is_contains;
        int current_index = 0;
        for(WebElement webElement:webElements){
            current_index++;
            actual_text = webElement.getText();
            is_contains = actual_text.contains(expected_text);
            Assert.assertTrue(error_message + " for element #" + current_index, is_contains);
        }
        return webElements;
    }

    public List <WebElement> waitAndGetWebElements(String locator, String error_message){
        By by = this.getLocatorByString(locator);
        waitForElementPresent(locator, error_message, 5);
        return driver.findElements(by);
    }

    public void swipeUp(WaitOptions timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(PointOption.point(x, start_y))
                .waitAction(timeOfSwipe)
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    public void swipeUpQuick(){
        swipeUp(new WaitOptions().withDuration(ofSeconds(1)));
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes){
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if(already_swiped > max_swipes){
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(String locator, String error_message){
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(new WaitOptions().withDuration(ofMillis(300)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message){
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0){
            String default_message = "An element '" + locator.toString() + "' suppose to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent(String locator, String error_message){
        By by = this.getLocatorByString(locator);
        try {
            driver.findElement(by);
        }
        catch(Exception e){
            String default_message = "An element '" + locator + "' suppose to be present.";
            throw new AssertionError(default_message + " " + error_message);
        }

    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        }else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }
}
