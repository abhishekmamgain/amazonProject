package com.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Product class to hold product data
    public static class Product {
        public String title;
        public String price;
        public String rating;
        public String reviews;

        public Product(String title, String price, String rating, String reviews) {
            this.title = title;
            this.price = price;
            this.rating = rating;
            this.reviews = reviews;
        }
    }

    // Get filtered product list
    public List<Product> getFilteredProducts(double minPrice, double maxPrice, double minRating) {
        List<Product> filteredProducts = new ArrayList<>();

        // Wait for results to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a-spacing-top-small")));

        // Get all product blocks
        List<WebElement> products = driver.findElements(By.cssSelector("div.s-main-slot > div[data-component-type='s-search-result']"));

        for (WebElement product : products) {
            try {
                String title = product.findElement(By.cssSelector("h2 a span")).getText();

                String priceWhole = product.findElement(By.cssSelector("span.a-price-whole")).getText().replace(",", "");
                String priceFraction = product.findElement(By.cssSelector("span.a-price-fraction")).getText();
                double price = Double.parseDouble(priceWhole + "." + priceFraction);

                String ratingText = product.findElement(By.cssSelector("span.a-icon-alt")).getText().split(" ")[0];
                double rating = Double.parseDouble(ratingText);

                String reviewCount = product.findElement(By.cssSelector("span.a-size-base")).getText();

                // Filter based on price and rating
                if (price >= minPrice && price <= maxPrice && rating >= minRating) {
                    filteredProducts.add(new Product(title, "$" + price, String.valueOf(rating), reviewCount));
                }

            } catch (Exception e) {
                // If any data is missing, skip the product
                continue;
            }
        }

        return filteredProducts;
    }

    // Click the first product
    public void clickFirstProduct() {
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("div.s-main-slot > div[data-component-type='s-search-result'] h2 a")));
        firstProduct.click();
    }
}
