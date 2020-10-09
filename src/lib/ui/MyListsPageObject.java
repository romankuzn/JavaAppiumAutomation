package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    public static final String
            ELEMENT_BY_TEXT_TPL = "//*[@text='{TEXT}']";

    /* TEMPLATES METHODS */
    private static String getElementByText(String text) {
        return ELEMENT_BY_TEXT_TPL.replace("{TEXT}", text);
    }
    /* TEMPLATES METHODS */

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getElementByText(name_of_folder);

        // added to ensure page is loaded and correct element will be clicked in the next step
        this.waitForElementPresent(
                By.xpath(folder_name_xpath),
                "Cannot find created folder",
                5);

        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find created folder",
                5
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getElementByText(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title " + article_title,
                15
                );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getElementByText(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void swipeArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getElementByText(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find saved article"
                );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void openArticleByName(String article_title)
    {
        String article_xpath = getElementByText(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot find saved article",
                15
        );
    }


}
