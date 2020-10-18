package Tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class ArticleTests extends CoreTestCase
{
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testVerifyArticleHasTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Boeing 777");
        SearchPageObject.clickSearchResult();

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.assertElementPresent(
                ArticlePageObject.getTitleElement(),
                "Cannot find article title"
        );
    }
}
