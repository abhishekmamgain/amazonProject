# 🛒 AmazonScraper

This project automates the product search, filtering, and data extraction from [Amazon.com](https://www.amazon.com) using **Selenium WebDriver**. It extracts details like Product Title, Price, Rating, and Review Count for products that meet specific criteria, and saves them into a **CSV file**.

---

## 📌 Features

- Automated product search (e.g., “wireless headphones”)
- Applies rating filter (4 stars and above)
- Filters products within a specific price range (e.g., $50 - $200)
- Extracts:
  - Product Title
  - Price
  - Rating
  - Number of Reviews
- Saves data to `output/products.csv`
- Verifies the first product’s details from the list against its product page
- Works on both **Chrome** and **Firefox**
- Implements **Page Object Model (POM)** design
- Includes error handling and logging

---

## 🧰 Tech Stack

- Java 8+
- Maven
- Selenium WebDriver
- Apache Commons CSV
- ChromeDriver & GeckoDriver (Firefox)
- JUnit/TestNG (optional)

---

## 📁 Project Structure

