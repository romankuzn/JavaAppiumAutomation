package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]",
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_BY_TITLE_AND_DESC_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{TITLE}']/../*[@text='{DESC}']",
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_FOR_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
        SEARCH_RESULT_TITLE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']",
        SEARCH_RESULT_TITLE_NUMBER = "xpath:(//*[@resource-id='org.wikipedia:id/page_list_item_title'])[{NUMBER}]";
    ;

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultTitleElementByNumber(int number){
        return SEARCH_RESULT_TITLE_NUMBER.replace("{NUMBER}", String.valueOf(number));
    }

    private static String getResultSearchElement(String title, String desc){
        return SEARCH_RESULT_BY_TITLE_AND_DESC_TPL.replace("{TITLE}", title).replace("{DESC}", desc);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button!", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button!", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button.", 5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void waitForElementByTitleAndDescription(String title, String desc){
        String search_result_xpath = getResultSearchElement(title, desc);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with title " + title + " and description " + desc
        );
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request ",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_FOR_EMPTY_RESULT_ELEMENT, "Cannot find empty result element.", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void waitForCancelButtonNotPresent()
    {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Cancel Button is present",
                15
        );
    }

    public String getSearchResultTitle(int number)
    {
        this.waitForElementPresent(
                getResultTitleElementByNumber(number),
                "Cannot find result with number " + String.valueOf(number),
                15
        );

        return this.waitForElementAndGetAttribute(
                getResultTitleElementByNumber(number),
                "text",
                "Cannot find result with number " + String.valueOf(number),
                15
                );
    }

    public void clickSearchResult()
    {
        this.waitForElementAndClick(
                SEARCH_RESULT_TITLE,
                "Cannot find search result by Xpath " + SEARCH_RESULT_TITLE,
                15
        );
    }

}
