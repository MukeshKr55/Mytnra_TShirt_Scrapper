package com.myntra.scraper.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TShirtsScrapperMethods {

    private final Page page;
    private final List<TShirt> tShirtList = new ArrayList<>();

    /**
     * Constructor to initialize the page object.
     *
     * @param page Playwright page instance
     */
    public TShirtsScrapperMethods(Page page) {
        this.page = page;
    }

    /**
     * Hovers over the specified category on the Myntra homepage.
     *
     * @param category The category to hover on
     */
    public void hoverOnCategory(String category) {
        try {
            page.waitForSelector("a[data-group='" + category.toLowerCase() + "']").hover();
            System.out.println("Hovered on category: " + category);
        } catch (Exception e) {
            System.err.println("Error hovering on category: " + category + " - " + e.getMessage());
        }
    }

    /**
     * Clicks on the T-shirts section.
     */
    public void clickOnSection() {
        try {
            page.waitForTimeout(1000);
            page.waitForSelector("//a[contains(@href, 'men-tshirts')]").click();
            System.out.println("Clicked on T-shirts section.");
        } catch (Exception e) {
            System.err.println("Error clicking on T-shirts section: " + e.getMessage());
        }
    }

    /**
     * Checks if the specified brand is available.
     *
     * @param brand The brand name to check
     * @return True if the brand is available, false otherwise
     */
    public boolean isBrandAvailable(String brand) {
        try {
            page.waitForSelector("//label[contains(text(), '" + brand + "')]");
            boolean isAvailable = page.isVisible("//label[contains(text(), '" + brand + "')]");
            System.out.println("Brand " + brand + " availability: " + isAvailable);
            return isAvailable;
        } catch (Exception e) {
            System.err.println("Error checking brand availability for: " + brand + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches for a specific brand using the search bar.
     *
     * @param brand The brand name to search for
     */
    public void searchBrandName(String brand) {
        try {
            page.waitForLoadState();
            page.waitForSelector("//input[@placeholder='Search for Brand']/following-sibling::span[@class='myntraweb-sprite filter-search-iconSearch sprites-search']").click();
            page.waitForSelector("//input[@placeholder='Search for Brand']").fill(brand);
            page.keyboard().press("Enter");
            System.out.println("Searched for brand: " + brand);
        } catch (Exception e) {
            System.err.println("Error searching for brand: " + brand + " - " + e.getMessage());
        }
    }

    /**
     * Selects the specified brand from the filter options.
     *
     * @param brand The brand name to select
     */
    public void selectBrand(String brand) {
        try {
            page.waitForSelector(String.format("//input[@value='%s']/following-sibling::div", brand));
            page.locator(String.format("//input[@value='%s']/following-sibling::div", brand)).click();
            System.out.println("Selected brand: " + brand);
        } catch (Exception e) {
            System.err.println("Error selecting brand: " + brand + " - " + e.getMessage());
        }
    }

    /**
     * Extracts T-shirt data including price, discount, and product link.
     */
    public void extractItems() {
        try {
            page.waitForTimeout(2000);
            page.waitForSelector("//li[@class='product-base']");
            List<Locator> items = page.locator("//li[@class='product-base']").all();

            for (Locator item : items) {
                try {
                    if (item.locator(".product-discountedPrice").count() == 0) continue;

                    String priceText = item.locator(".product-discountedPrice").textContent().trim();
                    String discountText = item.locator(".product-discountPercentage").textContent().trim();
                    String link = item.locator("a").getAttribute("href");

                    if (priceText != null && link != null) {
                        int price = Integer.parseInt(priceText.replace("Rs. ", "").trim());
                        int discount = Integer.parseInt(discountText.replaceAll("[^0-9]", "").trim());
                        if (discount >= 100) continue;

                        tShirtList.add(new TShirt(price, discount, "https://www.myntra.com/" + link));
                    }
                } catch (TimeoutError | NumberFormatException e) {
                    System.err.println("Error processing product: " + e.getMessage());
                }
            }
            System.out.println("Extracted data for " + tShirtList.size() + " T-shirts.");
        } catch (Exception e) {
            System.err.println("Error extracting items: " + e.getMessage());
        }
    }

    /**
     * Sorts the extracted T-shirts by highest discount.
     */
    public void sortDiscountedTShirts() {
        try {
            tShirtList.sort(Comparator.comparingInt(TShirt::getDiscount).reversed());
            System.out.println("Sorted T-shirts by highest discount.");
        } catch (Exception e) {
            System.err.println("Error sorting T-shirts: " + e.getMessage());
        }
    }

    /**
     * Prints the sorted list of T-shirts to the console.
     */
    public void printTShirts() {
        try {
            tShirtList.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error printing T-shirts: " + e.getMessage());
        }
    }


public static class TShirt {
    private final int price;
    private final int discount;
    private final String link;

    public TShirt(int price, int discount, String link) {
        this.price = price;
        this.discount = discount;
        this.link = link;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "TShirt{" +
                "price=" + price +
                ", discount=" + discount + "%" +
                ", link='" + link + '\'' +
                '}';
    }
}
}

