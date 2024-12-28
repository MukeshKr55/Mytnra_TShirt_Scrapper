package com.myntra.scraper.steps;

import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class TShirtScraperSteps {

    private TShirtsScrapperMethods tshirtsScrapperMethods;

    /**
     * Navigate to the Myntra homepage and initialize necessary components.
     */
    @Given("I navigate to the Myntra homepage")
    public void navigateToMyntraHomepage() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://www.myntra.com/");
        System.out.println("Navigated to Myntra homepage.");

        tshirtsScrapperMethods = new TShirtsScrapperMethods(page);
    }

    /**
     * Hover over the specified category.
     * @param category The category to hover over.
     */
    @When("I select the {string} category")
    public void selectCategory(String category) {
        tshirtsScrapperMethods.hoverOnCategory(category);

        // Assert that the category menu is visible after hovering
        Assert.assertTrue("Category menu is not visible after hovering", tshirtsScrapperMethods.isCategoryVisible(category));
    }

    /**
     * Navigate to the T-shirts section.
     */
    @And("I navigate to the T-shirts section")
    public void navigateToSection() {
        tshirtsScrapperMethods.clickOnSection();
    }


    /**
     * Apply a filter by the specified brand.
     * @param brand The brand name to filter by.
     */
    @And("I filter the results by the brand {string}")
    public void filterByBrand(String brand) {
        tshirtsScrapperMethods.searchBrandName(brand);
        // Assert the brand is visible and available
        Assert.assertTrue("Brand not available. Please check the brand name.", tshirtsScrapperMethods.isBrandAvailable(brand));
        tshirtsScrapperMethods.selectBrand(brand);

    }

    /**
     * Extract discounted T-shirt data including price, discount percentage, and link.
     */
    @Then("I extract the discounted T-shirts' data including price, discount percentage, and link")
    public void extractDiscountedTShirtsData() {
        tshirtsScrapperMethods.extractItems();
        // Assert that items are extracted and the list is not empty
        Assert.assertFalse("No T-shirt data extracted", tshirtsScrapperMethods.getExtractedTShirts().isEmpty());
    }

    /**
     * Sort the extracted T-shirt data by the highest discount first.
     */
    @And("I sort the extracted data by the highest discount first")
    public void sortByHighestDiscount() {
        tshirtsScrapperMethods.sortDiscountedTShirts();
        // Assert that the list is sorted by discount in descending order
        Assert.assertTrue("T-shirts are not sorted by highest discount",
                tshirtsScrapperMethods.isSortedByDiscount());
    }

    /**
     * Print the sorted list of discounted T-shirts to the console.
     */
    @And("I print the sorted list of discounted T-shirts to the console")
    public void printSortedTShirts() {
        tshirtsScrapperMethods.printTShirts();
    }
}
