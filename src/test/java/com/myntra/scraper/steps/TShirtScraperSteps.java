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
        try {
            Playwright playwright = Playwright.create();
            // Initialize Playwright and launch browser
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://www.myntra.com/");
            System.out.println("Navigated to Myntra homepage.");

            // Initialize TShirtsScrapperMethods after the page is created
            tshirtsScrapperMethods = new TShirtsScrapperMethods(page);
        } catch (Exception e) {
            System.err.println("Failed to navigate to Myntra homepage.");
            e.printStackTrace();
        }
    }

    /**
     * Hover over the specified category.
     * @param category The category to hover over.
     */
    @When("I select the {string} category")
    public void selectCategory(String category) {
        try {
            tshirtsScrapperMethods.hoverOnCategory(category);
        } catch (Exception e) {
            System.err.println("Failed to select category: " + category);
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the T-shirts section.
     */
    @And("I navigate to the T-shirts section")
    public void navigateToSection() {
        try {
            tshirtsScrapperMethods.clickOnSection();
        } catch (Exception e) {
            System.err.println("Failed to navigate to the T-shirts section.");
            e.printStackTrace();
        }
    }

    /**
     * Apply a filter by the specified brand.
     * @param brand The brand name to filter by.
     */
    @And("I filter the results by the brand {string}")
    public void filterByBrand(String brand) {
        try {
            tshirtsScrapperMethods.searchBrandName(brand);
            Assert.assertTrue("Brand not available. Please check the brand name.", tshirtsScrapperMethods.isBrandAvailable(brand));
            tshirtsScrapperMethods.selectBrand(brand);
        } catch (Exception e) {
            System.err.println("Failed to apply filter for brand: " + brand);
            e.printStackTrace();
        }
    }

    /**
     * Extract discounted T-shirt data including price, discount percentage, and link.
     */
    @Then("I extract the discounted T-shirts' data including price, discount percentage, and link")
    public void extractDiscountedTShirtsData() {
        try {
            tshirtsScrapperMethods.extractItems();
        } catch (Exception e) {
            System.err.println("Failed to extract discounted T-shirts' data.");
            e.printStackTrace();
        }
    }

    /**
     * Sort the extracted T-shirt data by the highest discount first.
     */
    @And("I sort the extracted data by the highest discount first")
    public void sortByHighestDiscount() {
        try {
            tshirtsScrapperMethods.sortDiscountedTShirts();
        } catch (Exception e) {
            System.err.println("Failed to sort T-shirts by discount.");
            e.printStackTrace();
        }
    }

    /**
     * Print the sorted list of discounted T-shirts to the console.
     */
    @And("I print the sorted list of discounted T-shirts to the console")
    public void printSortedTShirts() {
        try {
            tshirtsScrapperMethods.printTShirts();
        } catch (Exception e) {
            System.err.println("Failed to print sorted T-shirts.");
            e.printStackTrace();
        }
    }
}
