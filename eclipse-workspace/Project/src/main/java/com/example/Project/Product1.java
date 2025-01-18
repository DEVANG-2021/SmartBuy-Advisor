//Product.java

package com.example.Project;

public class Product1 {
    // Attributes of the Product class, representing different properties of a product

    private String name;              // Product name or title
    private String price;             // Price of the product in a string format
    private String gps;               // Indicates if the product has GPS functionality
    private String connectivity;      // Type of connectivity (e.g., Bluetooth, Wi-Fi)
    private String heartRateMonitor;  // Indicates if the product includes a heart rate monitor
    private String os;                // Operating system associated with the product
    private String touchscreen;       // Indicates if the product has touchscreen capability
    private String batteryLife;       // Expected battery life duration
    private String storage;           // Storage capacity available in the product
    private String imageURL;          // URL to an image representing the product

    // Constructor to initialize the Product object with all attributes
    public Product1(String name, String price, String gps, String connectivity, String heartRateMonitor, String os,
                   String touchscreen, String batteryLife, String storage, String imageURL) {
        // Set each attribute based on parameters passed to the constructor
        this.name = name;
        this.price = price;
        this.gps = gps;
        this.connectivity = connectivity;
        this.heartRateMonitor = heartRateMonitor;
        this.os = os;
        this.touchscreen = touchscreen;
        this.batteryLife = batteryLife;
        this.storage = storage;
        this.imageURL = imageURL;
    }

    // Getter methods to retrieve values of each attribute

    public String getName() {
        return name; // Return the product name
    }

    public String getPrice() {
        return price; // Return the product price
    }

    public String getGps() {
        return gps; // Return GPS functionality information
    }

    public String getConnectivity() {
        return connectivity; // Return connectivity options available
    }

    public String getHeartRateMonitor() {
        return heartRateMonitor; // Return heart rate monitor information
    }

    public String getOs() {
        return os; // Return the operating system of the product
    }

    public String getTouchscreen() {
        return touchscreen; // Return touchscreen feature information
    }

    public String getBatteryLife() {
        return batteryLife; // Return the battery life of the product
    }

    public String getStorage() {
        return storage; // Return the storage capacity
    }

    public String getImageURL() {
        return imageURL; // Return the image URL for the product
    }

}




