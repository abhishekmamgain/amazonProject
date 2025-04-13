package com.selenium.amazon.tests;

import com.selenium.amazon.pages.ProductPage;
import com.selenium.amazon.pages.SearchResultsPage;
import com.selenium.amazon.pages.SearchResultsPage.Product;
import com.selenium.amazon.utils.CSVWriter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Scanner;

public class AmazonProductTest {

    public static void main(String[] args) {
        WebDriver driver = null;

        // Step 1: Choose browser
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter browser (chrome/firefox): ");
        String browser = scanner.nextLine();

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            System.out.println("Unsupported browser");
            System.exit(1);
        }

        try {
            // Step 2: Open Amazon
            driver.manage().window().maximize();
            driver.get("https://www.amazon.com");

            // Step 3: Search for product
            driver.findElement(org.openqa.selenium.By.id("twotabsearchtextbox")).sendKeys("wireless headphones");
            driver.findElement(org.openqa.selenium.By.id("nav-search-submit-button")).click();

            // Wait a bit for filters to show and apply rating filter manually (or automate with logic if time)
            Thread.sleep(5000); // Simplified for demo

            // Step 4: Initialize SearchResultsPage
            SearchResultsPage resultsPage = new SearchResultsPage(driver);
            List<Product> filteredProducts = resultsPage.getFilteredProducts(4000, 16000, 4.0);

            if (filteredProducts.isEmpty()) {
                System.out.println("No matching products found.");
                return;
            }

            // Step 5: Save to CSV
            String filePath = "products.csv";
            CSVWriter.writeProductsToCSV(filteredProducts, filePath);
            System.out.println("Saved to " + filePath);

            // Step 6: Click first product
            Product firstProduct = filteredProducts.get(0);
            resultsPage.clickFirstProduct();

            // Step 7: Validate product details
            ProductPage productPage = new ProductPage(driver);
            boolean valid = productPage.validateProductDetails(firstProduct.title, firstProduct.price, firstProduct.rating, firstProduct.reviews);

            if (valid) {
                System.out.println("✅ Product details validated successfully.");
            } else {
                System.out.println("❌ Product details mismatch!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            scanner.close();
        }
    }
}
