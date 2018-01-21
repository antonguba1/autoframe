package example;

import com.codeborne.selenide.Configuration;
import example.pages.AmazonMainPage;
import example.pages.CartPage;
import example.pages.ItemPage;
import io.qameta.allure.*;
import listeners.BaseListener;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({BaseListener.class})
public class MyTests {

    @BeforeClass
    public void globalSetUp() {
        Configuration.reportsFolder = "target/selenideReports/tests";
    }

    @BeforeTest
    public void testSetUp() { }

    @AfterTest
    public void testTearDown() { }

    @Feature("Searching")
    @Story("Search by titles")
    @Severity(SeverityLevel.CRITICAL)
    @Parameters("searchString")
    @Test(description = "Check that a product can be found through the Search")
    public void testSearchingItems(String searchString) {
        new AmazonMainPage().searchFor(searchString)
                            .withResultsMoreThan(1)
                            .firstItemTitleContains("unit testing");
    }

	@Feature("Discounts")
    @Story("Discount content global updating")
    @Severity(SeverityLevel.MINOR)
    @Parameters("label")
    @Test(description = "Check the correct label for an item on the Today’s Deals page")
    public void testTodaysDealsAvailability(String label) {
        new AmazonMainPage().enterTodaysDeal()
                            .withResultsMoreThan(1)
                            .firstItemHasLabel(label);
    }

    @Feature("Purchasing")
    @Feature("User profile")
    @Story("Adding MP3 to the Cart")
    @Severity(SeverityLevel.BLOCKER)
    @Issue("777")
    @TmsLink("12345")
    @Test(description = "Check adding an album to the Cart")
    public void testAddingItemToCart() {
        ItemPage item = new AmazonMainPage().searchFor("Joe Satriani")
                                            .getFirstFound();

        String expectedTitle = item.getTitle();

        CartPage cart = item.switchToCD()
                            .addToCart()
                            .proceedToCheckout();

        Assert.assertEquals(expectedTitle, cart.getLastAddedItemTitle());
    }

}
