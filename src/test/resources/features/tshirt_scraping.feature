Feature: Myntra T-Shirt Scraper with Playwright & Cucumber BDD

  Scenario: Scrape discounted Van Heusen T-shirts from Myntra
    Given I navigate to the Myntra homepage
    When I select the "Men" category
    And I navigate to the T-shirts section
    And I filter the results by the brand "Van Heusen"
    Then I extract the discounted T-shirts' data including price, discount percentage, and link
    And I sort the extracted data by the highest discount first
    And I print the sorted list of discounted T-shirts to the console
