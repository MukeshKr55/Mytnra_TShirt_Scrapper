package com.myntra.scraper.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TShirtsScrapperMethods {

    private final Page page;
    private final List<TShirt> tShirtList = new ArrayList<>();

    public TShirtsScrapperMethods(Page page) {
        this.page = page;
    }

    public void hoverOnCategory(String category) {
        page.waitForSelector("a[data-group='" + category.toLowerCase() + "']").hover();
        System.out.println("Hovered on category: " + category);
    }

    public void clickOnSection() {
        page.waitForTimeout(1000);
        page.waitForSelector("//a[contains(@href, 'men-tshirts')]").click();
        System.out.println("Clicked on T-shirts section.");
    }

    public boolean isBrandAvailable(String brand) {
        page.waitForTimeout(2000);
        return page.isVisible("//label[contains(text(), '" + brand + "')]");
    }

    public void searchBrandName(String brand) {
        page.waitForLoadState();
        page.waitForSelector("//input[@placeholder='Search for Brand']/following-sibling::span[@class='myntraweb-sprite filter-search-iconSearch sprites-search']").click();
        page.waitForSelector("//input[@placeholder='Search for Brand']").fill(brand);
        page.keyboard().press("Enter");
        System.out.println("Searched for brand: " + brand);
    }

    public boolean isCategoryVisible(String category) {
        return page.isVisible("a[data-group='" + category.toLowerCase() + "']");
    }


    public List<TShirt> getExtractedTShirts() {
        return tShirtList;
    }

    public boolean isSortedByDiscount() {
        for (int i = 1; i < tShirtList.size(); i++) {
            if (tShirtList.get(i).getDiscount() > tShirtList.get(i - 1).getDiscount()) {
                return false;
            }
        }
        return true;
    }


    public void selectBrand(String brand) {
        page.waitForSelector(String.format("//input[@value='%s']/following-sibling::div", brand));
        page.locator(String.format("//input[@value='%s']/following-sibling::div", brand)).click();
        System.out.println("Selected brand: " + brand);
    }

    public void extractItems() {
        page.waitForTimeout(2000);
        page.waitForSelector("//li[@class='product-base']");
        List<Locator> items = page.locator("//li[@class='product-base']").all();

        for (Locator item : items) {
            if (item.locator(".product-discountedPrice").count() == 0) continue;

            String priceText = item.locator(".product-discountedPrice").textContent().trim();
            String discountText = item.locator(".product-discountPercentage").textContent().trim();
            String link = item.locator("a").getAttribute("href");

            if (priceText != null && link != null) {
                try {
                    int price = Integer.parseInt(priceText.replace("Rs. ", "").trim());
                    int discount = Integer.parseInt(discountText.replaceAll("[^0-9]", "").trim());
                    if (discount >= 100) continue;

                    tShirtList.add(new TShirt(price, discount, "https://www.myntra.com/" + link));
                } catch (NumberFormatException e) {
                    System.err.println("Error processing product: " + e.getMessage());
                }
            }
        }
        System.out.println("Extracted data for " + tShirtList.size() + " T-shirts.");
    }

    public void sortDiscountedTShirts() {
        tShirtList.sort(Comparator.comparingInt(TShirt::getDiscount).reversed());
        System.out.println("Sorted T-shirts by highest discount.");
    }

    public void printTShirts() {
        tShirtList.forEach(System.out::println);
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

        public int getDiscount() {
            return discount;
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
