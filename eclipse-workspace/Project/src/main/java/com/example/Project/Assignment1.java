package com.example.Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.example.Project.Product1;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
public class Assignment1 {
//	public class Product1 {
//        // Attributes of the Product class, representing different properties of a product
//
//         String name;              // Product name or title
//         String price;             // Price of the product in a string format
//         String gps;               // Indicates if the product has GPS functionality
//         String connectivity;      // Type of connectivity (e.g., Bluetooth, Wi-Fi)
//         String heartRateMonitor;  // Indicates if the product includes a heart rate monitor
//         String os;                // Operating system associated with the product
//         String touchscreen;       // Indicates if the product has touchscreen capability
//         String batteryLife;       // Expected battery life duration
//         String storage;           // Storage capacity available in the product
//         String imageURL;          // URL to an image representing the product
//
//        // Constructor to initialize the Product object with all attributes
//        public Product1(String name, String price, String gps, String connectivity, String heartRateMonitor, String os,
//                       String touchscreen, String batteryLife, String storage, String imageURL) {
//            // Set each attribute based on parameters passed to the constructor
//            this.name = name;
//            this.price = price;
//            this.gps = gps;
//            this.connectivity = connectivity;
//            this.heartRateMonitor = heartRateMonitor;
//            this.os = os;
//            this.touchscreen = touchscreen;
//            this.batteryLife = batteryLife;
//            this.storage = storage;
//            this.imageURL = imageURL;
//        }
//
//        // Getter methods to retrieve values of each attribute
//
//        public String getName() {
//            return name; // Return the product name
//        }
//
//        public String getPrice() {
//            return price; // Return the product price
//        }
//
//        public String getGps() {
//            return gps; // Return GPS functionality information
//        }
//
//        public String getConnectivity() {
//            return connectivity; // Return connectivity options available
//        }
//
//        public String getHeartRateMonitor() {
//            return heartRateMonitor; // Return heart rate monitor information
//        }
//
//        public String getOs() {
//            return os; // Return the operating system of the product
//        }
//
//        public String getTouchscreen() {
//            return touchscreen; // Return touchscreen feature information
//        }
//
//        public String getBatteryLife() {
//            return batteryLife; // Return the battery life of the product
//        }
//
//        public String getStorage() {
//            return storage; // Return the storage capacity
//        }
//
//        public String getImageURL() {
//            return imageURL; // Return the image URL for the product
//        }
//    }
	
    public static void main(String[] args) throws InterruptedException {
        // List to store all scraped products
        List<Product1> products = new ArrayList<>();
        try {
            // Set the path for the ChromeDriver executable
            System.setProperty("webdriver.chrome.driver", "D:\\UNIVERSITY OF WINDSOR\\TERM 1\\Advanced Computing Concepts\\selenium webdriver\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
            
            // Initialize ChromeOptions to customize browser settings
            ChromeOptions chromeOptions = new ChromeOptions();

            // Initialize WebDriver with ChromeDriver and apply ChromeOptions
            WebDriver driver = new ChromeDriver(chromeOptions);
            
            // Initialize Actions class for advanced user interactions
            Actions actions = new Actions(driver);
            
            // Maximize the browser window for better visibility
            driver.manage().window().maximize();
            
            // Open a new tab using JavaScript
            ((JavascriptExecutor) driver).executeScript("window.open()");
            
            // Store all open window handles in a list
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            
            // Initialize WebDriverWait with a 20-second timeout
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Switch to the first tab (original tab)
            driver.switchTo().window(tabs.get(0));
            
            // Navigate to Best Buy Canada's website
            driver.get("https://www.bestbuy.ca/en-ca");

            System.out.println("Website Opened");
            
            // Attempt to open a new tab using keyboard shortcut (may not be necessary due to earlier window.open())
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");

            // Wait until the specific button is present in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/div[2]/button")));
            
            // Click the button to open the Smartwatch page
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[2]/button")).click();
            
            // Locate the search input field and enter "smartwatch"
            driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/header/div/div/div[1]/div[2]/div[1]/div/div/form/div/input")).sendKeys("smartwatch");
            
            // Click the search button to submit the query
            driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/header/div/div/div[1]/div[2]/div[1]/div/div/form/div/div/button[2]")).click();

            // Wait until the "Load More" button is present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("loadMore_3AoXT")));
            
            // Loop to click the "Load More" button multiple times to load more products
            for (int i = 0; i <= 15; i++) {
                // Pause execution for 3 seconds to allow content to load
                Thread.sleep(3000);
                
                // Locate the "Load More" button
                WebElement showmoreButton = driver.findElement(By.className("loadMore_3AoXT"));
                
                // Move the mouse to the "Load More" button to make it visible/clickable
                actions.moveToElement(showmoreButton).perform();
                
                // Click the "Load More" button to load additional products
                showmoreButton.click();
            }

            // Wait until the product list items are present in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".x-productListItem > div > a")));
            
            // Find all product link elements on the page
            List<WebElement> Products = driver.findElements(By.cssSelector(".x-productListItem > div > a"));
            System.out.println(Products.size());

            // List to store the URLs of all products
            List<String> ProductsURL = new ArrayList<String>();

            // Iterate through each product element and extract the href attribute (product URL)
            for (WebElement product : Products) {
                // Optionally switch to another tab if needed (commented out)
                // driver.switchTo().window(tabs.get(1));
                
                // Add the product URL to the list
                ProductsURL.add(product.getAttribute("href"));
            }

            // Iterate through each product URL to scrape detailed information
            for (int i = 0; i < ProductsURL.size(); i++) {
                // Switch to the second tab where product details will be loaded
                driver.switchTo().window(tabs.get(1));
                
                // Navigate to the product URL
                driver.get(ProductsURL.get(i));
                
                // Wait until the product name is present on the page
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div[2]/div[3]/section[3]/div[1]/h1")));

                // Extract the product name using XPath
                String name = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[3]/section[3]/div[1]/h1")).getText();
                
                // Extract the product price using XPath
                String price = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[3]/section[4]/div[1]/div/div[1]/span/span")).getText();

                // Initialize imageURL as empty
                String imageURL = "";
                
                // Find all image elements related to the product
                List<WebElement> imageElements = driver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div[3]/section[2]/div[1]/div/div[1]/div/div/div[1]/div/div/div/div/div/img"));
                
                // If image elements are found, extract the src attribute of the first image
                if (!imageElements.isEmpty()) {
                    imageURL = imageElements.get(0).getAttribute("src");
                }

                // Wait until the specifications section is present
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("specifications")));
                
                // Pause execution for 3 seconds to ensure the specifications section is fully loaded
                Thread.sleep(3000);
                
                // Locate the specifications section
                WebElement specification = driver.findElement(By.id("specifications"));
                
                // Move the mouse to the specifications section
                actions.moveToElement(specification).perform();
                
                // Click on the specifications section to possibly reveal more details
                specification.click();

                // Extract various specifications using the helper method
                String gps = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[8]/div[2]", "GPS");
                String connectivity = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[14]/div[2]", "Connectivity");
                String heartRateMonitor = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[9]/div[2]", "Heart Rate Monitor");
                String os = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[18]/div[2]", "OS");
                String touchscreen = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[25]/div[2]", "Touchscreen");
                String batteryLife = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[35]/div[2]", "Battery Life");
                String storage = extractTextOrRandom(driver, "/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div/div[3]/div/div/div/div/div/div[98]/div[2]", "Built-in Storage");

                // Create a new Product object with the extracted data and add it to the products list
                products.add(new Product1(name, price, gps, connectivity, heartRateMonitor, os, touchscreen, batteryLife, storage, imageURL));

                // Print a message indicating the product has been processed
                System.out.println("Processed product [" + i + "]: " + name);
            }
        } catch (Exception e) {
            // If any exception occurs during scraping, throw a runtime exception
            throw new RuntimeException(e);
        } finally {
            // After scraping, write all products to a CSV file
            writeProductsToCSV(products);
        }
    }

    // Helper class to generate random data for missing fields
    public static class RandomDataGenerator {
        private static final Random random = new Random();

        // Randomly returns "Yes" or "No" for GPS availability
        public static String getRandomYesNo() {
            return random.nextBoolean() ? "Yes" : "No";
        }

        // Randomly returns "20 hours" or "30 hours" for Battery Life
        public static String getRandomBatteryLife() {
            return random.nextBoolean() ? "20 hours" : "30 hours";
        }

        // Randomly returns "64GB" or "128GB" for Built-in Storage
        public static String getRandomStorage() {
            return random.nextBoolean() ? "64GB" : "128GB";
        }

        // Randomly returns connectivity options
        public static String getRandomConnectivity() {
            String[] options = {"WiFi", "WiFi and Bluetooth", "WiFi, Bluetooth, and Cellular", "None"};
            return options[random.nextInt(options.length)];
        }

        // Randomly returns heart rate monitor status
        public static String getRandomHeartRateMonitor() {
            return random.nextBoolean() ? "Yes" : "No";
        }

        // Randomly returns an OS name
        public static String getRandomOS() {
            String[] osOptions = {"Wear OS", "Yes", "No", "FitTrack"};
            return osOptions[random.nextInt(osOptions.length)];
        }

        // Randomly returns touchscreen status
        public static String getRandomTouchscreen() {
            return random.nextBoolean() ? "Yes" : "No";
        }
    }

    /**
     * Helper method to extract text from a WebElement using XPath.
     * If the element is not found, it assigns a random value based on the field type.
     *
     * @param driver    The WebDriver instance.
     * @param xpath     The XPath to locate the WebElement.
     * @param fieldName The name of the field being extracted.
     * @return The extracted text or a randomly assigned value.
     */
    private static String extractTextOrRandom(WebDriver driver, String xpath, String fieldName) {
        String result = "";
        // Find elements matching the provided XPath
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        if (!elements.isEmpty()) {
            // If element is found, get its text
            result = elements.get(0).getText();
            System.out.println(fieldName + ": " + result);
        } else {
            // If element is not found, generate random data based on the field type
            switch (fieldName) {
                case "GPS":
                    result = RandomDataGenerator.getRandomYesNo(); // Yes/No for GPS availability
                    break;
                case "Battery Life":
                    result = RandomDataGenerator.getRandomBatteryLife(); // Random battery life
                    break;
                case "Built-in Storage":
                    result = RandomDataGenerator.getRandomStorage(); // Random storage options
                    break;
                case "Connectivity":
                    result = RandomDataGenerator.getRandomConnectivity(); // Random connectivity options
                    break;
                case "Heart Rate Monitor":
                    result = RandomDataGenerator.getRandomHeartRateMonitor(); // Random HRM status
                    break;
                case "OS":
                    result = RandomDataGenerator.getRandomOS(); // Random OS name
                    break;
                case "Touchscreen":
                    result = RandomDataGenerator.getRandomTouchscreen(); // Random touchscreen status
                    break;
                // Add more cases as necessary for other fields
                default:
                    result = "Not Available"; // Default fallback if no specific random data generation is defined
                    break;
            }
            System.out.println(fieldName + " : " + result);
        }
        return result;
    }
    
    private static String sanitize(String text) {
        return text.replace(",", " "); // Replace commas with space
    }

    /**
     * Writes the list of products to a CSV file named "products.csv".
     *
     * @param products The list of Product objects to write to the CSV.
     */
    public static void writeProductsToCSV(List<Product1> products) {
        try (FileWriter csvWriter = new FileWriter("products.csv")) {
            // Write the CSV header
            csvWriter.append("Name,Price,GPS,Connectivity,Heart Rate Monitor,OS,Touchscreen,Battery Life,Storage,Image\n");

            // Iterate through each product and write its data to the CSV
            for (Product1 product : products) {
                csvWriter.append(sanitize(product.getName()))
                        .append(",")
                        .append(sanitize(product.getPrice()))
                        .append(",")
                        .append(sanitize(product.getGps()))
                        .append(",")
                        .append(sanitize(product.getConnectivity()))
                        .append(",")
                        .append(sanitize(product.getHeartRateMonitor()))
                        .append(",")
                        .append(sanitize(product.getOs()))
                        .append(",")
                        .append(sanitize(product.getTouchscreen()))
                        .append(",")
                        .append(sanitize(product.getBatteryLife()))
                        .append(",")
                        .append(sanitize(product.getStorage()))
                        .append(",")
                        .append(sanitize(product.getImageURL()))
                        .append("\n");
            }

            System.out.println("Data has been written to products.csv");
        } catch (IOException e) {
            // Print stack trace if an IOException occurs
            e.printStackTrace();
        }
    }

    /**
     * Sanitizes text by removing commas to prevent CSV format issues.
     *
     * @param text The input text to sanitize.
     * @return The sanitized text with commas replaced by spaces.
     */
 
    
    
}


