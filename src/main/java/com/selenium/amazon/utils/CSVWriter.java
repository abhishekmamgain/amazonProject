package com.selenium.amazon.utils;

import com.selenium.amazon.pages.SearchResultsPage.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public static void writeProductsToCSV(List<Product> products, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Header
            writer.append("Product Title,Price,Rating,Number of Reviews\n");

            // Product rows
            for (Product p : products) {
                writer.append(p.title.replace(",", " ")) // avoid breaking CSV with commas
                      .append(",")
                      .append(p.price)
                      .append(",")
                      .append(p.rating)
                      .append(",")
                      .append(p.reviews)
                      .append("\n");
            }

            writer.flush();
            System.out.println("✅ CSV file written successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error writing CSV file: " + e.getMessage());
        }
    }
}
