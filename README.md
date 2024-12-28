# Myntra T-Shirt Scraper with Playwright & Cucumber BDD

This project is an automated scraper built using Playwright and Cucumber BDD to scrape discounted Van Heusen T-shirts from Myntra.com. The scraper navigates the website, applies filters, extracts data, sorts the results, and outputs the information in a sorted manner.

## Features
- Scrapes discounted Van Heusen T-shirts from Myntra.
- Filters by brand (Van Heusen).
- Extracts product data including price, discount percentage, and product link.
- Sorts the T-shirts based on the highest discount.
- Prints the sorted list to the console.

## Technologies Used
- **Java**: Programming language used to implement the scraper.
- **Playwright**: Used for browser automation to interact with the Myntra website.
- **Cucumber BDD**: For behavior-driven development, written in Gherkin syntax.
- **JUnit**: For assertions and test execution.
- **Maven**: Dependency management and build tool.

## Installation

### Prerequisites
- **Java** (JDK 11 or later)
- **Maven** (For managing dependencies and building the project)
- **Playwright** (Java library for browser automation)

### Steps to Set Up:
1. **Clone the repository:**
   ```bash
     git clone https://github.com/yourusername/myntra-tshirt-scraper.git
     cd myntra-tshirt-scraper
   ```
2. **Install the dependencies:**
   ```bash
     mvn clean install
   ```
3. **Run the project:**
    ```bash
     mvn test
    ```

## Bugs and Improvements Reporting

### Bug Report

- **Issue**: On the first page, the number of displayed items is less than 50, even though 50 items are shown in the inspector.
- **Steps to Reproduce**:
  1. Navigate to Myntra's Men -> T-shirts section.
  2. Apply the "Van Heusen" filter from the sidebar.
  3. Observe the first page of results.
- **Expected Behavior**: The first page should display 50 items, matching the count in the inspector.
- **Impact**: This issue affects the consistency of the product listing on the first page.

### Improvement

- **Suggestion**: Adjust the layout or item loading logic to ensure that the full set of filtered products appears correctly on the first page.
- **Benefit**: This will improve the user experience and ensure the expected results are consistently displayed.
