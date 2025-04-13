package com.selenium.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getProductTitle() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
        return title.getText().trim();
    }

    public String getProductPrice() {
        try {
            WebElement price = driver.findElement(By.cssSelector("#priceblock_ourprice, #priceblock_dealprice, #priceblock_saleprice"));
            return price.getText().trim();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getRating() {
        try {
            WebElement rating = driver.findElement(By.cssSelector("span[data-asin] span.a-icon-alt"));
            return rating.getText().split(" ")[0];
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getReviewCount() {
        try {
            WebElement reviews = driver.findElement(By.id("acrCustomerReviewText"));
            return reviews.getText().split(" ")[0].replace(",", "");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public boolean validateProductDetails(String expectedTitle, String expectedPrice, String expectedRating, String expectedReviews) {
        String actualTitle = getProductTitle();
        String actualPrice = getProductPrice();
        String actualRating = getRating();
        String actualReviews = getReviewCount();

        System.out.println("Validating product page:");
        System.out.println("Title: " + actualTitle);
        System.out.println("Price: " + actualPrice);
        System.out.println("Rating: " + actualRating);
        System.out.println("Reviews: " + actualReviews);

        return actualTitle.contains(expectedTitle) &&
               actualPrice.contains(expectedPrice.replace("$", "").split("\\.")[0]) &&
               actualRating.equals(expectedRating) &&
               actualReviews.equals(expectedReviews);
    }
}
